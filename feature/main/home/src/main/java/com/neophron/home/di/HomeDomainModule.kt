package com.neophron.home.di

import com.neophron.home.domain.repositories.ProductsRepository
import com.neophron.home.domain.usecases.GetFlashSaleProductsUseCase
import com.neophron.home.domain.usecases.GetLatestProductsUseCase
import com.neophron.home.domain.usecases.GetSearchProductsUseCase
import dagger.Module
import dagger.Provides

@Module
class HomeDomainModule {

    @Provides
    fun provideGetLatestProductsUseCase(repository: ProductsRepository): GetLatestProductsUseCase =
        GetLatestProductsUseCase(repository)

    @Provides
    fun provideGetFlashSaleProductsUseCase(repository: ProductsRepository): GetFlashSaleProductsUseCase =
        GetFlashSaleProductsUseCase(repository)

    @Provides
    fun provideGetSearchProductsUseCase(repository: ProductsRepository): GetSearchProductsUseCase =
        GetSearchProductsUseCase(repository)

}