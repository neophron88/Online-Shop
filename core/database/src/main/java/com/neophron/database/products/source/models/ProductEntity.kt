package com.neophron.database.products.source.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
class ProductEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "group_id") @PrimaryKey(autoGenerate = true) val groupId: Long,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "discount") val discount: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String
)