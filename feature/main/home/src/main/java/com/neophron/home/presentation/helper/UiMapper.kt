package com.neophron.home.presentation.helper

import android.app.Application
import com.neophron.home.R
import com.neophron.home.domain.models.Product
import com.neophron.home.presentation.models.ProductDisplay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface UiMapper {

    suspend fun mapToDisplay(products: List<Product>): List<ProductDisplay>

}

class UiMapperImpl(
    private val dispatcher: CoroutineDispatcher,
    private val application: Application
) : UiMapper {

    override suspend fun mapToDisplay(products: List<Product>): List<ProductDisplay> =
        withContext(dispatcher) {
            products.map { it.toDisplay() }
        }


    private fun Product.toDisplay(): ProductDisplay =
        ProductDisplay(
            id,
            groupId,
            category,
            name,
            application.getString(R.string.dollar_pattern, price),
            discount?.let { application.getString(R.string.discount_pattern, discount, "%") },
            imageUrl
        )

}

