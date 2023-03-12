package com.neophron.home.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.contract.main_feature.MainBottomNavHeightProvider
import com.neophron.feature.contract.main_feature.MainBottomNavProvider
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.home.R
import com.neophron.home.databinding.HomeFragmentBinding
import com.neophron.home.di.HomeAssistedFactoryProvider
import com.neophron.home.presentation.ui.viewHolders.ProductSearchViewHolder
import com.neophron.home.presentation.ui.viewModel.HomeUiEvent
import com.neophron.home.presentation.ui.viewModel.HomeUiState
import com.neophron.home.presentation.ui.viewModel.HomeViewModel
import com.neophron.mylibrary.ktx.doAfterTextStopChanging
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.ktx.fragment.viewLifeCycle
import com.neophron.mylibrary.ktx.showToast
import com.neophron.mylibrary.rv_adapter_delegate.ItemDelegate
import com.neophron.mylibrary.rv_adapter_delegate.ItemDiffUtil
import com.neophron.mylibrary.rv_adapter_delegate.ItemsAdapter
import com.neophron.mylibrary.viewbinding_delegate.viewBindings
import java.io.File

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBindings()

    private val contentAdapter by viewLifeCycle { setupContentAdapter() }

    private val searchProductAdapter by viewLifeCycle { setupSearchProductAdapter() }

    private val bottomNav by viewLifeCycle {
        findParentAs<MainBottomNavProvider>().getBottomNav()
    }

    private val viewModel: HomeViewModel by viewModelProvider {
        findParentAs<DependencyProvider>()
            .extractDependency<HomeAssistedFactoryProvider>()
            .getHomeFactory()
            .create()

    }

    internal val viewPool by viewLifeCycle { RecyclerView.RecycledViewPool() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurePaddingRespectToBottomNav()
        setupSearchingProducts()
        setupSearchProductList()
        setupContentList()
        setupRefreshContent()
        observeUiState()
        observeUiEvent()
    }

    private fun configurePaddingRespectToBottomNav() {
        findParentAs<MainBottomNavHeightProvider>().setBottomNavHeightListener { height ->
            binding.contentList.updatePadding(bottom = height)
        }
    }

    private fun setupSearchProductAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.text_item,
            diffUtil = ItemDiffUtil(itemsTheSameValue = String::toString),
            VHProducer = { ProductSearchViewHolder(view = it, onTextClick = ::updateSearchField) },
        )
    )

    private fun updateSearchField(text: String) = binding.apply {
        editSearch.clearFocus()
        editSearch.setText(text)
        viewModel.resetSearchProducts()
    }

    private fun setupSearchingProducts() = binding.apply {
        editSearch.doAfterTextStopChanging(delay = 1000) {
            val searchText = it.toString().trim()
            if (searchText.isNotBlank()) viewModel.searchProducts(searchText)
            else viewModel.resetSearchProducts()
        }
    }


    private fun setupSearchProductList() = binding.searchSuggestionList.apply {
        adapter = searchProductAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupContentList() = binding.contentList.apply {
        adapter = contentAdapter
        itemAnimator = null
        layoutManager = LinearLayoutManager(requireContext())
        setRecycledViewPool(viewPool)
    }

    private fun setupRefreshContent() = binding.apply {
        refreshLayout.setOnRefreshListener { viewModel.refreshContent() }
    }

    private fun observeUiState() = viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)

    private fun updateUi(uiState: HomeUiState) = binding.apply {
        refreshLayout.isRefreshing = uiState.isContentLoading
        if (uiState.contentList.isNotEmpty()) contentAdapter.submitList(uiState.contentList)

        uiState.account?.let { account ->
            Glide.with(this@HomeFragment)
                .load(account.avatarUrl?.let { File(it) })
                .error(com.neophron.ui.R.drawable.test_profile_image)
                .into(avatar)
        }
    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        if (it is HomeUiEvent.ToastMessage) bottomNav.showToast(it.msgRes)
        else if (it is HomeUiEvent.SearchingResult) {
            if (isThereOnlyOneElement(it.suggestions) &&
                isTheOneElementEqualsToEditSearchText(it.suggestions)
            ) return@observe

            searchProductAdapter.submitList(it.suggestions)
        }
    }

    private fun isThereOnlyOneElement(list: List<String>) = list.size == 1

    private fun isTheOneElementEqualsToEditSearchText(list: List<String>) =
        list[0] == binding.editSearch.text.toString().trim()
}