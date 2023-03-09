package com.neophron.product_detail.domain.repositories

import com.neophron.product_detail.domain.models.ProductDetailQuery
import com.neophron.product_detail.domain.result.ProductDetailResult

interface ProductDetailRepository {

    suspend fun getProductDetail(query: ProductDetailQuery): ProductDetailResult
}