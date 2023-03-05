package com.neophron.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.account.domain.result.AccountErrorType
import com.neophron.account.domain.result.AccountResult
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {

    private val _uiEvent: MutableSingleUseData<MainUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<MainUiEvent> get() = _uiEvent

    init {
        observeAccount()
    }

    private fun observeAccount() = viewModelScope.launch {
        getAccountUseCase().collect(::processUiEvent)
    }

    private fun processUiEvent(accountResult: AccountResult) {
        if (accountResult is AccountResult.Error)
            _uiEvent.value = MainUiEvent(accountResult.type is AccountErrorType.NoAuthorized)
    }
}