package com.neophron.product_detail.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.contract.main_feature.MainNavigator
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.mylibrary.ktx.dp
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
import com.neophron.product_detail.presentation.viewHolders.BigProductItemViewHolder
import com.neophron.product_detail.presentation.viewHolders.ColorItemViewHolder
import com.neophron.product_detail.presentation.viewHolders.SmallPromoProductItemViewHolder
import java.util.*
import kotlin.math.abs

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
    }

    private fun setupNavigateUp() = binding.navigateUp.setOnClickListener {
        findNavController().navigateUp()
    }


    private fun setupRefreshLayout() = Unit

    private fun setupBigPromoAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.big_promo_product_item,
            diffUtil = ItemDiffUtil(String::toString),
            VHProducer = { BigProductItemViewHolder(it) }
        )
    )

    private fun setupBigPromoPager() {
        binding.bigPromoPager.adapter = bigPromoAdapter
    }

    private fun setupSmallPromoAdapter() = ItemsAdapter(
        ItemDelegate(
            layout = R.layout.small_promo_product_item,
            diffUtil = ItemDiffUtil(String::toString),
            VHProducer = {
                SmallPromoProductItemViewHolder(it) {
                    Log.d("it0088", "setupSmallPromoAdapter: $it")
                }
            }
        )
    )

    private fun setupSmallPromoPager() = binding.smallPromoPager.apply {
        adapter = smallPromoAdapter
        offscreenPageLimit = 3
        setPageTransformer(CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(10.dp(requireContext())))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        })
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

    private fun setupQuantityCounter() {
    }

    private fun observeUiState() =
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)

    private fun updateUi(uiState: ProductDetailUiState) = binding.apply {
        val product = uiState.productDetail ?: return@apply
        name.text = product.name
        price.text = getString(R.string.dollar_pattern, product.price)
        description.text = product.description
        rating.text = String.format(Locale.getDefault(), ".2f", product.rating)
        reviews.text = getString(R.string.reviews_pattern, product.numberOfReviews)

        bigPromoAdapter.submitList(product.imageUrls)
        smallPromoAdapter.submitList(product.imageUrls)
        colorsAdapter.submitList(product.colors)

    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        if (it is ProductDetailUiEvent.ToastMessage) showToast(it.msgRes)
    }
}