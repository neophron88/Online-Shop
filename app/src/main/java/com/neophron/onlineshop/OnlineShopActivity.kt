package com.neophron.onlineshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.neophron.feature.contract.app.AppNavigator
import com.neophron.mylibrary.ktx.findNavController

class OnlineShopActivity : AppCompatActivity(), AppNavigator {

    private val navController: NavController by lazy {
        supportFragmentManager.findNavController(R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawableResource(com.neophron.ui.R.drawable.day_night)
    }

    override fun navigateFromSplashToAuth(args: Bundle?) {
        navController.navigate(R.id.action_splashFragment_to_authHostFragment)
    }

    override fun navigateFromSplashToMain(args: Bundle?) {
        navController.navigate(R.id.action_splashFragment_to_mainFragment)
    }

    override fun navigateFromAuthToMain(args: Bundle?) {
        navController.navigate(R.id.action_authHostFragment_to_mainFragment)
    }

    override fun navigateToSplashAndPopAllScreens() {
        navController.navigate(R.id.splashFragment, null, navOptions {
            popUpTo(R.id.splashFragment) { inclusive = true }
        })
    }
}