package com.neophron.onlineshop

import android.app.Application
import com.neophron.feature.contract.DependencyProvider
import com.neophron.onlineshop.di.DaggerOnlineShopAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class OnlineShopApp : Application(), DependencyProvider {

    private val applicationScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override val dependency: Any by lazy {
        DaggerOnlineShopAppComponent
            .factory()
            .create(this, applicationScope)
    }
}