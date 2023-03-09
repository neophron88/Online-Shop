package com.neophron.database.products.source.models

class RefreshQuery(
    val groupId: Long,
    val products: List<ProductEntity>
)