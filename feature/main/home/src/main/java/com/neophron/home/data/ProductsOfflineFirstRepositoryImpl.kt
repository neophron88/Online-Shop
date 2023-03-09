package com.neophron.home.data

import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.database.products.source.models.ProductsQuery
import com.neophron.database.products.source.models.RefreshQuery
import com.neophron.home.data.helper.mapToListOfProductEntity
import com.neophron.home.data.helper.mapToProducts
import com.neophron.home.data.helper.toErrorType
import com.neophron.home.domain.models.ProductsGroup
import com.neophron.home.domain.models.SearchQuery
import com.neophron.home.domain.repositories.ProductsRepository
import com.neophron.home.domain.result.BaseErrorType
import com.neophron.home.domain.result.ProductResult
import com.neophron.home.domain.result.SearchResult
import com.neophron.mylibrary.ktx.tryCatch
import com.neophron.mylibrary.offline_first.offlineFirst
import com.neophron.network.products.source.ProductsNetworkDataSource
import com.neophron.network.products.source.models.ProductResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ProductsOfflineFirstRepositoryImpl(
    private val longLiveScope: CoroutineScope,
    private val local: ProductsLocalDataSource,
    private val network: ProductsNetworkDataSource
) : ProductsRepository {

    override fun getLatest(): Flow<ProductResult> =
        getOfflineFirstProductsFlow(
            groupId = LATEST_GROUP_ID,
            groupName = LATEST,
            networkLoad = { network.loadLatest() }
        )

    override fun getFlashSale(): Flow<ProductResult> =
        getOfflineFirstProductsFlow(
            groupId = FLASH_SALE_GROUP_ID,
            groupName = FLASH_SALE,
            networkLoad = { network.loadFlashSale() }
        )

    override suspend fun searchProducts(searchQuery: SearchQuery): SearchResult =
        tryCatch(
            action = {
                val words = network.searchProduct().words
                val filteredWords = words.filter { it.startsWith(searchQuery.search, true) }
                SearchResult.Success(filteredWords)
            },
            onError = { exception -> SearchResult.Error(exception.toErrorType()) }
        )


    private fun getOfflineFirstProductsFlow(
        groupId: Long,
        groupName: String,
        networkLoad: suspend () -> List<ProductResponse>
    ): Flow<ProductResult> = offlineFirst(
        longLiveScope = longLiveScope,
        localDataFlow = { local.fetchProducts(ProductsQuery(groupId)) },
        syncWithNetwork = { networkLoad() },
        updateLocalData = {
            local.refreshProducts(
                RefreshQuery(groupId, it.mapToListOfProductEntity(groupId))
            )
        },
        pending = { ProductResult.Pending },
        syncedError = { ProductResult.Error(it.toErrorType()) },
        result = { productEntities ->
            productEntities?.let {
                ProductResult.Success(ProductsGroup(groupId, groupName, it.mapToProducts()))
            } ?: ProductResult.Error(BaseErrorType.Unknown())
        }
    )


    companion object {
        private const val LATEST_GROUP_ID = 1L
        private const val LATEST = "Latest"
        private const val FLASH_SALE_GROUP_ID = 2L
        private const val FLASH_SALE = "Flash Sale"
    }
}