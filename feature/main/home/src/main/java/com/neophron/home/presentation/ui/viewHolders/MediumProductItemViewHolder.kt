package com.neophron.home.presentation.ui.viewHolders

import android.view.View
import androidx.core.view.isInvisible
import com.neophron.home.databinding.MediumProductItemBinding
import com.neophron.home.presentation.models.ProductDisplay
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder


typealias OnMediumProductClick = (productId: Long) -> Unit

class MediumProductItemViewHolder(
    view: View,
    private val onProductClick: OnMediumProductClick
) : ItemViewHolder<ProductDisplay>(view) {

    private val binding = MediumProductItemBinding.bind(view)


    init {
        setupClickListener()
    }


    override fun onBind(item: ProductDisplay) = with(binding) {
        category.text = item.category
        name.text = item.name
        price.text = item.price
    }


    private fun setupClickListener() =
        binding.root.setOnClickListener { onProductClick(item.id) }

}

