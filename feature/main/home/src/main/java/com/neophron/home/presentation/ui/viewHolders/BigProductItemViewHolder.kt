package com.neophron.home.presentation.ui.viewHolders

import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.neophron.home.databinding.BigProductItemBinding
import com.neophron.home.presentation.models.ProductDisplay
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder


typealias OnBigProductClick = (productId: Long) -> Unit

class BigProductItemViewHolder(
    view: View,
    private val onProductClick: OnBigProductClick
) : ItemViewHolder<ProductDisplay>(view) {

    private val binding = BigProductItemBinding.bind(view)


    init {
        setupClickListener()
    }


    override fun onBind(item: ProductDisplay) = with(binding) {
        Glide.with(root).load(item.imageUrl).into(productImage)

        discount.isInvisible = item.discount == null
        discount.text = item.discount
        category.text = item.category
        name.text = item.name
        price.text = item.price

    }


    private fun setupClickListener() =
        binding.root.setOnClickListener { onProductClick(item.id) }

}

