package com.neophron.home.presentation.ui.viewHolders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neophron.home.databinding.BigBlockItemBinding
import com.neophron.home.domain.models.Product
import com.neophron.home.presentation.models.BigBlockDisplay
import com.neophron.mylibrary.rv_adapter_delegate.ItemDelegate
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.mylibrary.rv_adapter_delegate.ItemsAdapter


class BigBlockViewHolder(
    view: View,
    private val productViewPool: RecyclerView.RecycledViewPool,
    private val productItemDelegate: ItemDelegate<Product>,
) : ItemViewHolder<BigBlockDisplay>(view) {

    private val binding = BigBlockItemBinding.bind(view)
    private val productsAdapter = ItemsAdapter(productItemDelegate)


    init {
        setupList()
    }


    override fun onBind(item: BigBlockDisplay) = with(binding) {
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
            setRecycledViewPool(productViewPool)
        }

    }

}

