package com.neophron.network.products.source_impl.retrofit.models

import com.neophron.network.products.source.models.ProductResponse
import com.squareup.moshi.Json

class LatestDataResponse(
    @field:Json(name = "latest")
    val data: List<ProductResponse>
)