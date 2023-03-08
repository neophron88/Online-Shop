package com.neophron.database.products.source_impl

import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.database.products.source.models.ProductEntity
import com.neophron.database.products.source.models.ProductsQuery
import com.neophron.database.products.source_impl.room.ProductsDao
import kotlinx.coroutines.flow.Flow

internal class ProductsRoomDataSource(
    private val productsDao: ProductsDao
) : ProductsLocalDataSource {

    override fun fetchProducts(query: ProductsQuery): Flow<List<ProductEntity>> =
        productsDao.fetchProducts(query.groupId)

    override suspend fun refreshProducts(products: List<ProductEntity>) =
        productsDao.refreshProducts(products)

}