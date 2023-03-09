package com.neophron.home.presentation.ui

import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.neophron.feature.contract.main_feature.MainNavigator
import com.neophron.home.R
import com.neophron.home.presentation.models.BigBlockDisplay
import com.neophron.home.presentation.models.MediumBlockDisplay
import com.neophron.home.presentation.models.ProductDisplay
import com.neophron.home.presentation.ui.viewHolders.BigBlockViewHolder
import com.neophron.home.presentation.ui.viewHolders.BigProductItemViewHolder
import com.neophron.home.presentation.ui.viewHolders.MediumBlockViewHolder
import com.neophron.home.presentation.ui.viewHolders.MediumProductItemViewHolder
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.rv_adapter_delegate.ItemDelegate
import com.neophron.mylibrary.rv_adapter_delegate.ItemDiffUtil
import com.neophron.mylibrary.rv_adapter_delegate.ItemsAdapter


internal fun HomeFragment.setupContentAdapter(): ItemsAdapter {

    val bigBlockItemDelegate = ItemDelegate(
        layout = R.layout.big_block_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = BigBlockDisplay::id),
        VHProducer = { createBigBlockViewHolder(it) }
    )

    val mediumBlockItemDelegate = ItemDelegate(
        layout = R.layout.medium_block_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = MediumBlockDisplay::id),
        VHProducer = { createMediumBlockViewHolder(it) },
    )

    return ItemsAdapter(bigBlockItemDelegate, mediumBlockItemDelegate).also {
        it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
}

private fun HomeFragment.createBigBlockViewHolder(view: View) = BigBlockViewHolder(
    view = view,
    productViewPool = viewPool,
    productItemDelegate = ItemDelegate(
        layout = R.layout.big_product_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = ProductDisplay::id),
        VHProducer = {
            BigProductItemViewHolder(view = it, onProductClick = ::navigateToProductDetail)
        }
    )
)


private fun HomeFragment.createMediumBlockViewHolder(view: View) = MediumBlockViewHolder(
    view = view,
    productViewPool = viewPool,
    productItemDelegate = ItemDelegate(
        layout = R.layout.medium_product_item,
        diffUtil = ItemDiffUtil(itemsTheSameValue = ProductDisplay::id),
        VHProducer = {
            MediumProductItemViewHolder(view = it, onProductClick = ::navigateToProductDetail)
        }
    )
)

private fun HomeFragment.navigateToProductDetail(productId: Long) =
    findParentAs<MainNavigator>()
        .navigateToProductDetail(
            bundleOf(MainNavigator.PRODUCT_ID to productId)
        )


