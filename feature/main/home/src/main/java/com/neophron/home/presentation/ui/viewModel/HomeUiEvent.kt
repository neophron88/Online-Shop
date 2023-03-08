package com.neophron.home.presentation.ui.viewModel

import androidx.annotation.StringRes

sealed class HomeUiEvent {
    class ToastMessage(@StringRes msgRes: Int) : HomeUiEvent()
}