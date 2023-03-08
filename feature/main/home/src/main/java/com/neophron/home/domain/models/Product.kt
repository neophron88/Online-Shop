package com.neophron.home.domain.models

data class Product(
    val id: Long,
    val groupId: Long,
    val category: String,
    val name: String,
    val price: Double,
    val discount: Int?,
    val imageUrl: String
)
