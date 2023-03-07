package com.neophron.network.products.source.models

import com.squareup.moshi.Json

class ProductResponse(
    @field:Json(name = "category")
    val category: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "price")
    val price: String,
    @field:Json(name = "discount")
    val discount: String?,
    @field:Json(name = "image_url")
    val imageUrl: String,
)