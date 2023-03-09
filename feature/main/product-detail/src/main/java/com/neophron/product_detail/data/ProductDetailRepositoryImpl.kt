package com.neophron.product_detail.data

import android.util.Log
import com.neophron.mylibrary.ktx.tryCatch
import com.neophron.network.product_detail.source.ProductDetailNetworkDataSource
import com.neophron.product_detail.data.helper.mapToProductDetail
import com.neophron.product_detail.data.helper.toErrorType
import com.neophron.product_detail.domain.models.ProductDetailQuery
import com.neophron.product_detail.domain.repositories.ProductDetailRepository
import com.neophron.product_detail.domain.result.ProductDetailResult

class ProductDetailRepositoryImpl(
    private val network: ProductDetailNetworkDataSource
) : ProductDetailRepository {

    override suspend fun getProductDetail(query: ProductDetailQuery): ProductDetailResult =
        tryCatch(
            action = {
                val detail = network.loadProductDetail(query.productId).mapToProductDetail()
                ProductDetailResult.Success(detail)
            },
            onError = { exception -> ProductDetailResult.Error(exception.toErrorType()) })

}