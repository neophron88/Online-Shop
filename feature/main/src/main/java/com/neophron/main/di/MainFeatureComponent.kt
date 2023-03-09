package com.neophron.main.di

import android.app.Application
import com.neophron.account.domain.usecases.ChangeAvatarUseCase
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.account.domain.usecases.LogOutUseCase
import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.home.di.HomeAssistedFactoryProvider
import com.neophron.home.di.HomeDataModule
import com.neophron.home.di.HomeDomainModule
import com.neophron.home.di.HomePresentationModule
import com.neophron.network.product_detail.source.ProductDetailNetworkDataSource
import com.neophron.network.products.source.ProductsNetworkDataSource
import com.neophron.product_detail.di.ProductDetailAssistedFactoryProvider
import com.neophron.product_detail.di.ProductDetailDataModule
import com.neophron.product_detail.di.ProductDetailDomainModule
import com.neophron.profile.di.ProfileAssistedFactoryProvider
import dagger.Component
import kotlinx.coroutines.CoroutineScope


@MainFeatureScope
@Component(
    dependencies = [MainFeatureDependencies::class],
    modules = [
        HomeDataModule::class, HomeDomainModule::class, HomePresentationModule::class,
        ProductDetailDataModule::class, ProductDetailDomainModule::class
    ]

)
interface MainFeatureComponent :
    MainAssistedFactoryProvider,
    HomeAssistedFactoryProvider,
    ProfileAssistedFactoryProvider,
    ProductDetailAssistedFactoryProvider {

    @Component.Builder
    interface Builder {

        fun dependencies(deps: MainFeatureDependencies): Builder

        fun build(): MainFeatureComponent
    }
}

interface MainFeatureDependencies {

    fun getAccountUseCase(): GetAccountUseCase

    fun getChangeAvatarUseCase(): ChangeAvatarUseCase

    fun getLogOutUseCase(): LogOutUseCase

    fun getProductsLocalDataSource(): ProductsLocalDataSource

    fun getProductsNetworkDataSource(): ProductsNetworkDataSource

    fun getProductDetailNetworkDataSource(): ProductDetailNetworkDataSource

    fun getLongLiveScope(): CoroutineScope

    fun getApplication(): Application

}