package com.neophron.home.data.helper

import com.neophron.database.products.source.models.ProductEntity
import com.neophron.home.domain.models.Product
import com.neophron.network.products.source.models.ProductResponse

fun List<ProductResponse>.mapToListOfProductEntity(groupId: Long) =
    this.map { it.mapToProductEntity(groupId) }


fun ProductResponse.mapToProductEntity(groupId: Long) =
    ProductEntity(0, groupId, category, name, price, discount, imageUrl)

fun List<ProductEntity>.mapToProducts() =
    this.map { it.mapToProduct() }

fun ProductEntity.mapToProduct() =
    Product(id, groupId, category, name, price, discount, imageUrl)
