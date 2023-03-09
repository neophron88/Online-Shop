package com.neophron.home.presentation.ui.viewHolders

import android.view.View
import com.neophron.home.databinding.TextItemBinding
import com.neophron.mylibrary.rv_adapter_delegate.ItemViewHolder

typealias OnTextClick = (text: String) -> Unit

class ProductSearchViewHolder(
    view: View,
    private val onTextClick: OnTextClick
) : ItemViewHolder<String>(view) {

    private val binding = TextItemBinding.bind(view)

    init {
        binding.textView.setOnClickListener { onTextClick(item) }
    }

    override fun onBind(item: String) {
        binding.textView.text = item
    }

}



