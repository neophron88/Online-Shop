package com.neophron.home.domain.repositories

import com.neophron.home.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getLatest(): Flow<ProductResult>

    fun getFlashSale(): Flow<ProductResult>

}