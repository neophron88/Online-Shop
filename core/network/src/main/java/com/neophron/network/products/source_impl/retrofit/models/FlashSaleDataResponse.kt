package com.neophron.network.products.source_impl.retrofit.models

import com.neophron.network.products.source.models.ProductResponse
import com.squareup.moshi.Json

class FlashSaleDataResponse(
    @field:Json(name = "flash_sale")
    val data: List<ProductResponse>
)