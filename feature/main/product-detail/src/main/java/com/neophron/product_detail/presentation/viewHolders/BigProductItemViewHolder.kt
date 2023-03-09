package com.neophron.product_detail.presentation.viewHolders

import android.view.View
import com.bumptech.glide.Glide
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.product_detail.databinding.BigPromoProductItemBinding

class BigProductItemViewHolder(
    view: View
) : ItemViewHolder<String>(view) {

    private val binding = BigPromoProductItemBinding.bind(view)

    override fun onBind(item: String) {
        Glide.with(binding.root)
            .load(item)
            .into(binding.image)
    }
}