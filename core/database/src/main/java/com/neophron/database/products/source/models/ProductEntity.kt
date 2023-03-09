package com.neophron.database.products.source.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "product_table",
    indices = [Index("group_id", unique = false)]
)
class ProductEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "group_id") val groupId: Long,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "discount") val discount: Int?,
    @ColumnInfo(name = "image_url") val imageUrl: String
)