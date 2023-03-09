package com.neophron.home.di

import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.home.data.ProductsOfflineFirstRepositoryImpl
import com.neophron.home.domain.repositories.ProductsRepository
import com.neophron.network.products.source.ProductsNetworkDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module
class HomeDataModule {

    @Provides
    fun provideProductsRepository(
        longLiveScope: CoroutineScope,
        localSource: ProductsLocalDataSource,
        networkSource: ProductsNetworkDataSource
    ): ProductsRepository =
        ProductsOfflineFirstRepositoryImpl(
            longLiveScope, localSource, networkSource
        )


}