package com.neophron.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.neophron.feature.contract.AppNavigator
import com.neophron.mylibrary.ktx.postDelayed
import com.neophron.mylibrary.takeAs

class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() = viewLifecycleOwner.postDelayed(1000) {
        requireActivity().takeAs<AppNavigator>().navigateFromSplashToAuth()
    }
}