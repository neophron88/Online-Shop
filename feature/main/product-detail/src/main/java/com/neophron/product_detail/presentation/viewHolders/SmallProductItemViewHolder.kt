package com.neophron.product_detail.presentation.viewHolders

import android.view.View
import com.bumptech.glide.Glide
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.product_detail.databinding.SmallPromoProductItemBinding

typealias onItemClick = (position: Int) -> Unit

class SmallPromoProductItemViewHolder(
    view: View,
    private val onItemClick: onItemClick
) : ItemViewHolder<String>(view) {

    private val binding = SmallPromoProductItemBinding.bind(view)

    init {
        binding.root.setOnClickListener { onItemClick(bindingAdapterPosition) }
    }

    override fun onBind(item: String) {
        Glide.with(binding.root)
            .load(item)
            .into(binding.image)
    }
}