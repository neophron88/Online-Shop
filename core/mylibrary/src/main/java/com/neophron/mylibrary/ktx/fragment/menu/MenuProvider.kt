package com.neophron.mylibrary.ktx.fragment.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle


typealias OnMenuItemSelected = (item: MenuItem) -> Boolean
typealias OnCreateMenu = (menu: Menu) -> Unit
typealias OnPrepareMenu = (menu: Menu) -> Unit


fun Fragment.addMenuProvider(
    @MenuRes menuRes: Int,
    onMenuItemSelected: OnMenuItemSelected = { false },
    onCreateMenu: OnCreateMenu = {},
    onPrepareMenu: OnPrepareMenu = {}
) {

    val menuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
            onCreateMenu(menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            onMenuItemSelected(menuItem)


        override fun onPrepareMenu(menu: Menu) {
            super.onPrepareMenu(menu)
            onPrepareMenu(menu)
        }

    }

    requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.STARTED)
}
