package com.neophron.home.presentation.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neophron.home.databinding.MediumBlockItemBinding
import com.neophron.home.presentation.models.MediumBlockDisplay
import com.neophron.home.presentation.models.ProductDisplay
import com.neophron.mylibrary.rv_adapter_delegate.ItemDelegate
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.mylibrary.rv_adapter_delegate.ItemsAdapter


class MediumBlockViewHolder(
    view: View,
    private val productViewPool: RecyclerView.RecycledViewPool,
    private val productItemDelegate: ItemDelegate<ProductDisplay>,
) : ItemViewHolder<MediumBlockDisplay>(view) {

    private val binding = MediumBlockItemBinding.bind(view)
    private val productsAdapter = ItemsAdapter(productItemDelegate)


    init {
        setupList()
    }


    override fun onBind(item: MediumBlockDisplay) = with(binding) {
        title.text = item.title
        productsAdapter.submitList(item.list)
    }

    override fun unBind() {
        super.unBind()
        productsAdapter.submitList(emptyList())
    }


    private fun setupList() {
        productViewPool.setMaxRecycledViews(productItemDelegate.viewType, 12)
        with(binding.list) {
            adapter = productsAdapter
            itemAnimator = null
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                .also { it.initialPrefetchItemCount = 4 }
            setRecycledViewPool(productViewPool)
        }

    }

}

