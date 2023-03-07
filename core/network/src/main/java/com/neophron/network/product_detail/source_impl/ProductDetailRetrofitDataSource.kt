package com.neophron.network.product_detail.source_impl

import com.neophron.network.product_detail.source.ProductDetailNetworkDataSource
import com.neophron.network.product_detail.source.models.ProductDetailResponse

internal class ProductDetailRetrofitDataSource(
    private val service: ProductDetailService
) : ProductDetailNetworkDataSource {

    override suspend fun loadProductDetail(productId: Long): ProductDetailResponse =
        service.loadProductDetail()
}