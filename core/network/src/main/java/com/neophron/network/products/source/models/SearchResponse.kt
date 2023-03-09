package com.neophron.network.products.source.models

import com.squareup.moshi.Json

class SearchResponse(
    @field:Json(name = "words")
    val words: List<String>,
)