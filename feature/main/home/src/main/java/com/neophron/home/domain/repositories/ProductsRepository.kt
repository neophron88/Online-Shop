package com.neophron.home.domain.repositories

import com.neophron.home.domain.models.SearchQuery
import com.neophron.home.domain.result.ProductResult
import com.neophron.home.domain.result.SearchResult
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getLatest(): Flow<ProductResult>

    fun getFlashSale(): Flow<ProductResult>

    suspend fun searchProducts(searchQuery: SearchQuery): SearchResult


}