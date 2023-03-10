package com.neophron.product_detail.presentation.viewHolders

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder
import com.neophron.product_detail.databinding.ColorItemBinding

class ColorItemViewHolder(
    view: View
) : ItemViewHolder<String>(view) {

    private val binding = ColorItemBinding.bind(view)

    override fun onBind(item: String) {

        binding.color.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item))
    }
}