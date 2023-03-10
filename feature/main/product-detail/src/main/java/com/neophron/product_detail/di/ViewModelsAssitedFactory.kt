package com.neophron.product_detail.di

import com.neophron.product_detail.presentation.ui.ProductDetailViewModel
import dagger.assisted.AssistedFactory


interface ProductDetailAssistedFactoryProvider {
    fun getProductDetailFactory(): ProductDetailAssistedFactory
}


@AssistedFactory
interface ProductDetailAssistedFactory {
    fun create(productId: Long): ProductDetailViewModel
}