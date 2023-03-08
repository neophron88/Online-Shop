package com.neophron.feature.contract.app

import android.os.Bundle

interface AppNavigator {

    fun navigateFromSplashToAuth(args: Bundle? = null)

    fun navigateFromSplashToMain(args: Bundle? = null)

    fun navigateFromAuthToMain(args: Bundle? = null)

    fun navigateToSplashAndPopAllScreens()
}