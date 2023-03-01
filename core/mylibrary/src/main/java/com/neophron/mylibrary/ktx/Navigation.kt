package com.neophron.mylibrary.ktx

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment


fun FragmentManager.findNavController(@IdRes containerId: Int) =
    (this.findFragmentById(containerId) as NavHostFragment).navController