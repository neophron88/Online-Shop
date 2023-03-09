package com.neophron.home.presentation.ui.viewModel

import androidx.annotation.StringRes

sealed class HomeUiEvent {
    class ToastMessage(@StringRes val msgRes: Int) : HomeUiEvent()
    class SearchingResult(val suggestions: List<String>) : HomeUiEvent()
}