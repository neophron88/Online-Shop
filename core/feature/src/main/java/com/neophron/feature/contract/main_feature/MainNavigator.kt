package com.neophron.feature.contract.main_feature

import android.os.Bundle

interface MainNavigator {

    fun navigateToProductDetail(args: Bundle? = null)


    companion object {
        const val PRODUCT_ID = "product_id"
    }
}