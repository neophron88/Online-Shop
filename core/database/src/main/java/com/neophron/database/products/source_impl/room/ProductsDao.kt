package com.neophron.database.products.source_impl.room

import androidx.room.*
import com.neophron.database.products.source.models.ProductEntity
import com.neophron.database.products.source.models.RefreshQuery
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProductsDao {

    @Query("SELECT * FROM product_table WHERE group_id=:groupId")
    abstract fun fetchProducts(groupId: Long): Flow<List<ProductEntity>>

    @Transaction
    open suspend fun refreshProducts(refreshQuery: RefreshQuery) {
        deleteAllProducts(refreshQuery.groupId)
        insertProducts(refreshQuery.products)
    }

    @Query("DELETE FROM product_table WHERE group_id=:groupId")
    internal abstract suspend fun deleteAllProducts(groupId: Long)

    @Insert
    internal abstract suspend fun insertProducts(products: List<ProductEntity>)

}