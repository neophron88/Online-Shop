package com.neophron.home.domain.models

data class ProductsGroup(
    val id: Long,
    val title: String,
    val products: List<Product>
)