package com.neophron.network.products.source

import com.neophron.network.products.source.models.ProductResponse
import com.neophron.network.products.source.models.SearchResponse

interface ProductsNetworkDataSource {

    suspend fun loadLatest(): List<ProductResponse>

    suspend fun loadFlashSale(): List<ProductResponse>

    suspend fun searchProduct(): SearchResponse

}