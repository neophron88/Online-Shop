package com.neophron.network.products.source

import com.neophron.network.products.source.models.ProductResponse

interface ProductsNetworkDataSource {

    suspend fun loadLatest(): List<ProductResponse>

    suspend fun loadFlashSale(): List<ProductResponse>


}