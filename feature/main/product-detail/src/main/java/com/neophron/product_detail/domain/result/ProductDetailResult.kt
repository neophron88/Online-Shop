package com.neophron.product_detail.domain.result

import com.neophron.product_detail.domain.models.ProductDetail


sealed class ProductDetailResult {
    class Success(val detail: ProductDetail) : ProductDetailResult()
    class Error(val type: BaseErrorType) : ProductDetailResult()
}