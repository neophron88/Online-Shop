package com.neophron.main.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import com.neophron.feature.contract.app.AppNavigator
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.contract.main_feature.MainBottomNavHeightProvider
import com.neophron.feature.contract.main_feature.MainNavigator
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.main.R
import com.neophron.main.databinding.MainFragmentBinding
import com.neophron.main.di.DaggerMainFeatureComponent
import com.neophron.mylibrary.ktx.findNavController
import com.neophron.mylibrary.takeAs
import com.neophron.mylibrary.viewbinding_delegate.viewBindings

class MainFragment :
    Fragment(R.layout.main_fragment),
    MainBottomNavHeightProvider,
    MainNavigator,
    DependencyProvider {


    override val dependency by lazy {
        DaggerMainFeatureComponent
            .builder()
            .dependencies(requireActivity().application.extractDependency())
            .build()
    }

    private val viewModel: MainViewModel by viewModelProvider {
        dependency.getMainFactory().create()
    }
    private val childNavController by lazy {
        childFragmentManager.findNavController(R.id.tabsContainer)
    }

    private val binding: MainFragmentBinding by viewBindings()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNavigationView()
        observeAccount()

    }

    private fun observeAccount() = viewModel.uiEvent.observe(viewLifecycleOwner, ::processAccount)

    private fun processAccount(uiEvent: MainUiEvent) {
        if (uiEvent.isUnauthorized)
            requireActivity().takeAs<AppNavigator>().navigateToSplashAndPopAllScreens()
    }

    private fun setupBottomNavigationView() =
        NavigationUI.setupWithNavController(binding.bottomNavigationView, childNavController)


    override fun getBottomNavHeight(): Int =
        binding.bottomNavigationView.height

    override fun navigateToProductDetail(args: Bundle?) {
        childNavController.navigate(com.neophron.product_detail.R.id.product_detail_graph, args)
    }


}