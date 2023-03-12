package com.neophron.feature.contract.main_feature

interface MainBottomNavHeightProvider {

    fun setBottomNavHeightListener(callback: (height: Int) -> Unit)

}