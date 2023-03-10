package com.neophron.product_detail.presentation.viewHolders

import android.view.View
import com.bumptech.glide.Glide
import com.neophron.mylibrary.ktx.dp
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.product_detail.databinding.SmallPromoProductItemBinding
import com.neophron.product_detail.presentation.models.ProductImageDisplay


typealias onItemClick = (position: Int) -> Unit

class SmallPromoProductItemViewHolder(
    view: View,
    private val onItemClick: onItemClick
) : ItemViewHolder<ProductImageDisplay>(view) {

    private val binding = SmallPromoProductItemBinding.bind(view)
    private val context = binding.root.context

    init {
        binding.cardView.setOnClickListener { onItemClick(bindingAdapterPosition) }
    }

    override fun onBind(item: ProductImageDisplay) {
        binding.apply {
            Glide.with(root)
                .load(item.url)
                .into(binding.image)
            if (item.isChecked) changeScale(1.15f) else changeScale(1f)
            cardView.cardElevation =
                (if (item.isChecked) 12.dp(context) else 1.dp(context)).toFloat()
        }
    }

    private fun changeScale(value: Float) = binding.root.post {
        binding.cardView.animate()
            .scaleY(value)
            .scaleX(value)
            .start()
    }

    override fun unBind() {
        binding.cardView.apply {
            scaleX = 0f
            scaleY = 0f
        }
    }

}