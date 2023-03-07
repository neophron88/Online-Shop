package com.neophron.network.product_detail.source.models

import com.squareup.moshi.Json

class ProductDetailResponse(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "rating")
    val rating: Double,
    @field:Json(name = "number_of_reviews")
    val numberOfReviews: Int,
    @field:Json(name = "price")
    val price: Double,
    @field:Json(name = "colors")
    val colors: ColorsData,
    @field:Json(name = "image_urls")
    val imageUrls: ImageUrlsData
)