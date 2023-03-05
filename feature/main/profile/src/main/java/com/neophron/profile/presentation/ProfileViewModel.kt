package com.neophron.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.account.domain.models.ChangeAvatarData
import com.neophron.account.domain.result.AccountResult
import com.neophron.account.domain.result.BaseErrorType
import com.neophron.account.domain.result.ChangeAvatarResult
import com.neophron.account.domain.result.LogOutResult
import com.neophron.account.domain.usecases.ChangeAvatarUseCase
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.account.domain.usecases.LogOutUseCase
import com.neophron.account.presentation.toStringRes
import com.neophron.mylibrary.require
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import com.neophron.ui.R.string
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel @AssistedInject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val changeAvatarUseCase: ChangeAvatarUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<ProfileUiState> = MutableLiveData()
    val uiState: LiveData<ProfileUiState> get() = _uiState

    private val _uiEvent: MutableSingleUseData<ProfileUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<ProfileUiEvent> get() = _uiEvent


    init {
        observeAccount()
    }


    private fun observeAccount() = viewModelScope.launch {
        getAccountUseCase().collect {
            if (it is AccountResult.Success)
                _uiState.value = ProfileUiState(it.account)
        }
    }

    fun changeAvatar(avatarFile: File?) = viewModelScope.launch {
        _uiState.value = _uiState.value.require().copy(changeAvatarInProgress = true)
        val result = changeAvatarUseCase(ChangeAvatarData(avatarFile))
        if (result is ChangeAvatarResult.Error) {
            val type = result.type
            if (type is BaseErrorType) {
                _uiEvent.value = ProfileUiEvent.ToastMessage(type.toStringRes())
                _uiState.value = _uiState.value.require().copy(changeAvatarInProgress = false)
            }
        }
    }

    fun logOut() = viewModelScope.launch {
        val logOutResult = logOutUseCase()
        if (logOutResult is LogOutResult.Error)
            _uiEvent.value = ProfileUiEvent.ToastMessage(string.something_went_wrong)
    }
}
