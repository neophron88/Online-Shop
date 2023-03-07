package com.neophron.network.product_detail.source

import com.neophron.network.product_detail.source.models.ProductDetailResponse

interface ProductDetailNetworkDataSource {

    suspend fun loadProductDetail(productId: Long): ProductDetailResponse

}