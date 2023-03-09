package com.neophron.home.domain.usecases

import com.neophron.home.domain.models.SearchQuery
import com.neophron.home.domain.repositories.ProductsRepository
import com.neophron.home.domain.result.SearchResult

class GetSearchProductsUseCase(
    private val repository: ProductsRepository
) {

    suspend operator fun invoke(searchQuery: SearchQuery): SearchResult =
        repository.searchProducts(searchQuery)
}