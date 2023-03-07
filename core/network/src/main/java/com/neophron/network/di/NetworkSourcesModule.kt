package com.neophron.network.di

import com.neophron.database.account.AccountDao
import com.neophron.network.account.source.AccountNetworkDataSource
import com.neophron.network.account.source_impl.FakeNetworkAccountDataSource
import com.neophron.network.base.UserTokenStore
import com.neophron.network.product_detail.source.ProductDetailNetworkDataSource
import com.neophron.network.product_detail.source_impl.ProductDetailRetrofitDataSource
import com.neophron.network.product_detail.source_impl.ProductDetailService
import com.neophron.network.products.source.ProductsNetworkDataSource
import com.neophron.network.products.source_impl.ProductsRetrofitDataSource
import com.neophron.network.products.source_impl.retrofit.ProductsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class NetworkSourcesModule {

    @Provides
    @Singleton
    fun provideAccountNetworkDataSource(
        dao: AccountDao,
        userTokenStore: UserTokenStore
    ): AccountNetworkDataSource {
        return FakeNetworkAccountDataSource(dao, userTokenStore)
    }


    @Provides
    @Singleton
    fun provideProductsNetworkDataSource(
        retrofit: Retrofit
    ): ProductsNetworkDataSource {
        val service = retrofit.create(ProductsService::class.java)
        return ProductsRetrofitDataSource(service)
    }

    @Provides
    @Singleton
    fun provideProductDetailNetworkDataSource(
        retrofit: Retrofit
    ): ProductDetailNetworkDataSource {
        val service = retrofit.create(ProductDetailService::class.java)
        return ProductDetailRetrofitDataSource(service)
    }
}