@file:Suppress("FunctionName", "DEPRECATION")

package com.neophron.mylibrary.rv_adapter_delegate

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil

fun ItemsAdapter(vararg args: ItemDelegate<out Any>): ItemsAdapter {
    val builder = MediatorItemDelegate.Builder()
    args.forEach {
        builder.addItemDelegate(it)
    }
    return ItemsAdapter(builder.build())
}

inline fun <reified I : Any> ItemDelegate(
    @LayoutRes layout: Int,
    diffUtil: DiffUtil.ItemCallback<I>,
    noinline VHProducer: ItemViewHolderProducer<I>
): ItemDelegate<I> = ItemDelegate(I::class, layout, diffUtil, VHProducer)