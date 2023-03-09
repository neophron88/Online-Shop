package com.neophron.network.products.source_impl

import com.neophron.network.base.wrapRetrofitExceptions
import com.neophron.network.products.source.ProductsNetworkDataSource
import com.neophron.network.products.source.models.ProductResponse
import com.neophron.network.products.source.models.SearchResponse
import com.neophron.network.products.source_impl.retrofit.ProductsService

internal class ProductsRetrofitDataSource(
    private val service: ProductsService
) : ProductsNetworkDataSource {

    override suspend fun loadLatest(): List<ProductResponse> =
        wrapRetrofitExceptions { service.getLatest().data }

    override suspend fun loadFlashSale(): List<ProductResponse> =
        wrapRetrofitExceptions { service.getFlashSale().data }

    override suspend fun searchProduct(): SearchResponse =
        wrapRetrofitExceptions { service.searchProduct() }


}