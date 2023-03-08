package com.neophron.database.products.source

import com.neophron.database.products.source.models.ProductEntity
import com.neophron.database.products.source.models.ProductsQuery
import kotlinx.coroutines.flow.Flow

interface ProductsLocalDataSource {

    fun fetchProducts(query: ProductsQuery): Flow<List<ProductEntity>>

    suspend fun refreshProducts(products: List<ProductEntity>)

}