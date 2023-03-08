package com.neophron.home.presentation.models

data class ProductDisplay(
    val id: Long,
    val groupId: Long,
    val category: String,
    val name: String,
    val price: String,
    val discount: String?,
    val imageUrl: String
)
