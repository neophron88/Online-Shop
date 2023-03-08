package com.neophron.home.domain.usecases

import com.neophron.home.domain.repositories.ProductRepository
import com.neophron.home.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

class GetLatestProductsUseCase(
    private val repository: ProductRepository
) {

    operator fun invoke(): Flow<ProductResult> =
        repository.getLatest()
}