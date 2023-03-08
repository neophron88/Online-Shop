package com.neophron.home.domain.result

import com.neophron.home.domain.models.ProductsGroup


sealed class ProductResult {
    class Success(val productsGroup: ProductsGroup) : ProductResult()
    object Pending : ProductResult()
    class Error(val type: BaseErrorType) : ProductResult()
}