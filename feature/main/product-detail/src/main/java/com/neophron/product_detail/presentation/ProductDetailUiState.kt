package com.neophron.product_detail.presentation

import com.neophron.product_detail.domain.models.ProductDetail

data class ProductDetailUiState(
    val productDetail: ProductDetail? = null,
    val isLoading: Boolean = false
)