package com.neophron.product_detail.presentation.viewHolders

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.product_detail.databinding.BigPromoProductItemBinding
import com.neophron.product_detail.presentation.models.ProductImageDisplay

class BigPromoProductItemViewHolder(
    view: View
) : ItemViewHolder<ProductImageDisplay>(view) {

    private val binding = BigPromoProductItemBinding.bind(view)

    override fun onBind(item: ProductImageDisplay) {
        if (bindingAdapterPosition == 0) Log.d("itbig0088", "$this: ")
        Glide.with(binding.root)
            .load(item.url)
            .into(binding.image)

    }


}