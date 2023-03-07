package com.neophron.database.products.source_impl

import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.database.products.source.models.ProductEntity
import com.neophron.database.products.source.models.ProductsQuery
import com.neophron.database.products.source_impl.room.ProductsDao

internal class ProductsRoomDataSource(
    private val productsDao: ProductsDao
) : ProductsLocalDataSource {

    override suspend fun fetchProducts(query: ProductsQuery): List<ProductEntity> =
        productsDao.fetchProducts(query.groupId)

    override suspend fun refreshProducts(products: List<ProductEntity>) =
        productsDao.refreshProducts(products)

}