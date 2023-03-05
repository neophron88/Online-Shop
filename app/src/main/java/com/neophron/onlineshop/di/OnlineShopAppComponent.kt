package com.neophron.onlineshop.di

import android.app.Application
import com.neophron.account.di.data.AccountDataModule
import com.neophron.account.di.domain.AccountDomainModule
import com.neophron.auth.di.AuthFeatureDependencies
import com.neophron.database.di.DatabaseSourcesModule
import com.neophron.main.di.MainFeatureDependencies
import com.neophron.network.di.NetworkSourcesModule
import com.neophron.splash.di.SplashFeatureDependencies
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkSourcesModule::class,
        DatabaseSourcesModule::class,
        AccountDataModule::class,
        AccountDomainModule::class
    ]
)
interface OnlineShopAppComponent :
    SplashFeatureDependencies,
    AuthFeatureDependencies,
    MainFeatureDependencies {


    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance longLiveScope: CoroutineScope
        ): OnlineShopAppComponent
    }

}