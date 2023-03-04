package com.neophron.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.neophron.auth.R
import com.neophron.auth.databinding.AuthHostFragmentBinding
import com.neophron.auth.di.DaggerAuthFeatureComponent
import com.neophron.feature.contract.DependencyProvider
import com.neophron.feature.contract.extractDependency
import com.neophron.mylibrary.ktx.findNavController
import com.neophron.mylibrary.ktx.fragment.viewLifeCycle
import com.neophron.mylibrary.viewbinding_delegate.viewBindings

class AuthHostFragment : Fragment(R.layout.auth_host_fragment), DependencyProvider {


    private val binding: AuthHostFragmentBinding by viewBindings()

    private val childNavController: NavController by viewLifeCycle {
        childFragmentManager.findNavController(R.id.container)
    }

    private val navController: NavController by viewLifeCycle { findNavController() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val dependency by lazy {
        DaggerAuthFeatureComponent
            .builder()
            .dependencies(requireActivity().application.extractDependency())
            .build()
    }

}