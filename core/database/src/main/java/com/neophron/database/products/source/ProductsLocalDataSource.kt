package com.neophron.database.products.source

import com.neophron.database.products.source.models.ProductEntity
import com.neophron.database.products.source.models.ProductsQuery

interface ProductsLocalDataSource {

    suspend fun fetchProducts(query: ProductsQuery): List<ProductEntity>

    suspend fun refreshProducts(products: List<ProductEntity>)

}