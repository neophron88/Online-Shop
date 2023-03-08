package com.neophron.database.products.source_impl.room

import androidx.room.*
import com.neophron.database.products.source.models.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProductsDao {

    @Query("SELECT * FROM product_table WHERE group_id=:groupId")
    abstract fun fetchProducts(groupId: Long): Flow<List<ProductEntity>>

    @Transaction
    suspend fun refreshProducts(products: List<ProductEntity>) {
        deleteAllProducts()
        insertProducts(products)
    }

    @Query("DELETE FROM product_table")
    internal abstract suspend fun deleteAllProducts()

    @Insert
    internal abstract suspend fun insertProducts(products: List<ProductEntity>)

}