package com.neophron.splash.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.neophron.feature.contract.app.AppNavigator
import com.neophron.feature.contract.extractDependency
import com.neophron.mylibrary.ktx.postDelayed
import com.neophron.mylibrary.takeAs
import com.neophron.splash.R
import com.neophron.splash.di.DaggerSplashFeatureComponent
import com.neophron.splash.di.SplashFeatureComponent

class SplashFragment : Fragment(R.layout.fragment_splash) {


    private val component: SplashFeatureComponent by lazy {
        DaggerSplashFeatureComponent
            .builder()
            .dependencies(requireActivity().application.extractDependency())
            .build()
    }

    private val viewModel: SplashViewModel by viewModels { component.viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiEvent()
    }

    private fun observeUiEvent() =
        viewModel.uiEvent.observe(viewLifecycleOwner, ::scheduleNavigation)

    private fun scheduleNavigation(uiEvent: SplashUiEvent) = viewLifecycleOwner.postDelayed(1000) {
        if (uiEvent.isLoggedIn)
            requireActivity().takeAs<AppNavigator>().navigateFromSplashToMain()
        else
            requireActivity().takeAs<AppNavigator>().navigateFromSplashToAuth()
    }

}

