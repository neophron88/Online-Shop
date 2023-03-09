package com.neophron.product_detail.di

import com.neophron.network.product_detail.source.ProductDetailNetworkDataSource
import com.neophron.product_detail.data.ProductDetailRepositoryImpl
import com.neophron.product_detail.domain.repositories.ProductDetailRepository
import dagger.Module
import dagger.Provides

@Module
class ProductDetailDataModule {

    @Provides
    fun provideProductDetailRepository(
        networkSource: ProductDetailNetworkDataSource
    ): ProductDetailRepository =
        ProductDetailRepositoryImpl(networkSource)


}