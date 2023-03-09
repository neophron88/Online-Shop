package com.neophron.product_detail.di

import com.neophron.product_detail.domain.repositories.ProductDetailRepository
import com.neophron.product_detail.domain.usecases.GetProductDetailUseCase
import dagger.Module
import dagger.Provides

@Module
class ProductDetailDomainModule {

    @Provides
    fun provideGetGetProductDetailUseCase(repository: ProductDetailRepository): GetProductDetailUseCase =
        GetProductDetailUseCase(repository)

}