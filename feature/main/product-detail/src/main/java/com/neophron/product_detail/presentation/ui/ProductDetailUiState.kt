package com.neophron.product_detail.presentation.ui

import com.neophron.product_detail.presentation.models.ProductImageDisplay
import com.neophron.product_detail.presentation.models.ProductInfoDisplay

data class ProductDetailUiState(
    val detailInfo: ProductInfoDisplay? = null,
    val images: List<ProductImageDisplay>? = null,
    val colors: List<String>? = null,
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false
) {

    val dataIsEmpty get() = detailInfo == null
}