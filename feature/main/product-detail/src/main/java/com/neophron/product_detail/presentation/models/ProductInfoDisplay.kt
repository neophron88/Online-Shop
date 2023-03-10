package com.neophron.product_detail.presentation.models


data class ProductInfoDisplay(
    val name: String,
    val description: String,
    val rating: Double,
    val numberOfReviews: Int,
    val price: Double,
)