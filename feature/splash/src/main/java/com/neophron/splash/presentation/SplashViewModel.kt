package com.neophron.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.account.domain.result.AccountResult
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiEvent: MutableSingleUseData<SplashUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<SplashUiEvent> get() = _uiEvent

    init {
        checkAccount()
    }

    private fun checkAccount() = viewModelScope.launch {
        processUiEvent(getAccountUseCase().first())
    }

    private fun processUiEvent(accountResult: AccountResult) {
        _uiEvent.value = SplashUiEvent(accountResult is AccountResult.Success)
    }
}