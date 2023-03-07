package com.neophron.database.di

import com.neophron.database.account.AccountDao
import com.neophron.database.base.OnlineShopDatabase
import com.neophron.database.products.source.ProductsLocalDataSource
import com.neophron.database.products.source_impl.ProductsRoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DatabaseSourcesModule {

    @Provides
    @Singleton
    fun provideAccountDao(database: OnlineShopDatabase): AccountDao =
        database.getAccountDao()


    @Provides
    @Singleton
    fun provideProductsLocalDataSource(database: OnlineShopDatabase): ProductsLocalDataSource =
        ProductsRoomDataSource(database.getProductsDao())
}