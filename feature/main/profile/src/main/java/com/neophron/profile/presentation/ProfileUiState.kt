package com.neophron.profile.presentation

import com.neophron.account.domain.models.Account


data class ProfileUiState(
    val account: Account? = null,
    val changeAvatarInProgress: Boolean = false
)

