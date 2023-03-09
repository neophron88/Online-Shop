package com.neophron.product_detail.domain.usecases

import com.neophron.product_detail.domain.models.ProductDetailQuery
import com.neophron.product_detail.domain.repositories.ProductDetailRepository
import com.neophron.product_detail.domain.result.ProductDetailResult

class GetProductDetailUseCase(
    private val repository: ProductDetailRepository
) {
    suspend operator fun invoke(query: ProductDetailQuery): ProductDetailResult =
        repository.getProductDetail(query)
}