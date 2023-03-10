package com.neophron.product_detail.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.contract.main_feature.MainNavigator
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.ktx.fragment.viewLifeCycle
import com.neophron.mylibrary.ktx.showToast
import com.neophron.mylibrary.rv_adapter_delegate.ItemDelegate
import com.neophron.mylibrary.rv_adapter_delegate.ItemDiffUtil
import com.neophron.mylibrary.rv_adapter_delegate.ItemsAdapter
import com.neophron.mylibrary.viewbinding_delegate.viewBindings
import com.neophron.product_detail.R
import com.neophron.product_detail.databinding.ProductDetailsFragmentBinding
import com.neophron.product_detail.di.ProductDetailAssistedFactoryProvider
import com.neophron.product_detail.presentation.models.ProductImageDisplay
import com.neophron.product_detail.presentation.viewHolders.BigPromoProductItemViewHolder
import com.neophron.product_detail.presentation.viewHolders.ColorItemViewHolder
import com.neophron.product_detail.presentation.viewHolders.SmallPromoProductItemViewHolder
import java.util.*

class ProductDetailFragment : Fragment(R.layout.product_details_fragment) {

    private val binding: ProductDetailsFragmentBinding by viewBindings()
    private val viewModel: ProductDetailViewModel by viewModelProvider {
        findParentAs<DependencyProvider>()
            .extractDependency<ProductDetailAssistedFactoryProvider>()
            .getProductDetailFactory()
            .create(requireArguments().getLong(MainNavigator.PRODUCT_ID))
    }
    private val bigPromoAdapter by viewLifeCycle { setupBigPromoAdapter() }
    private val smallPromoAdapter by viewLifeCycle { setupSmallPromoAdapter() }
    private val colorsAdapter by viewLifeCycle { setupColorsAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigateUp()
        setupRefreshLayout()
        setupBigPromoPager()
        setupSmallPromoPager()
        setupColorsList()
        setupQuantityCounter()
        observeUiState()
        observeUiEvent()

        Log.d("it0088", "onViewCreated: ")
    }


    private fun setupNavigateUp() = binding.navigateUp.setOnClickListener {
        findNavController().navigateUp()
    }

    private fun setupRefreshLayout() = Unit

    private fun setupBigPromoAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.big_promo_product_item,
            diffUtil = ItemDiffUtil(ProductImageDisplay::url),
            VHProducer = { BigPromoProductItemViewHolder(it) }
        )
    )

    private fun setupBigPromoPager() = binding.apply {
        bigPromoPager.adapter = bigPromoAdapter
        bigPromoPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.selectImage(position)
                smallPromoPager.scrollToPosition(position)
            }
        })
    }

    private fun setupSmallPromoAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.small_promo_product_item,
            diffUtil = ItemDiffUtil(ProductImageDisplay::url),
            VHProducer = {
                SmallPromoProductItemViewHolder(it) { position ->
                    viewModel.selectImage(position)
                    binding.bigPromoPager.currentItem = position
                }
            }
        )
    )

    private fun setupSmallPromoPager() = binding.smallPromoPager.apply {
        adapter = smallPromoAdapter
        itemAnimator = null
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

    }


    private fun setupColorsList() {
        binding.colorList.adapter = colorsAdapter
    }


    private fun setupColorsAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.color_item,
            diffUtil = ItemDiffUtil(String::toString),
            VHProducer = { ColorItemViewHolder(it) }
        )
    )

    private fun setupQuantityCounter() = binding.apply {
        increase.setOnClickListener { viewModel.increaseQuantity() }
        decrease.setOnClickListener { viewModel.decreaseQuantity() }
    }

    private fun observeUiState() =
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)

    private fun updateUi(uiState: ProductDetailUiState) = binding.apply {
        val product = uiState.detailInfo ?: return@apply
        name.text = product.name
        price.text = getString(R.string.dollar_pattern, product.price)
        description.text = product.description
        rating.text = String.format(Locale.getDefault(), "%.1f", product.rating)
        reviews.text = getString(R.string.reviews_pattern, product.numberOfReviews)
        totalPrice.text = getString(R.string.dollar_pattern, uiState.totalPrice)
        uiState.images?.let {
            smallPromoAdapter.submitList(it)
            bigPromoAdapter.submitList(it)
        }
        uiState.colors?.let { colorsAdapter.submitList(it) }

    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        if (it is ProductDetailUiEvent.ToastMessage) showToast(it.msgRes)
    }
}