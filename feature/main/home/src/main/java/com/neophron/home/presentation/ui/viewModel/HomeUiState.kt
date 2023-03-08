package com.neophron.home.presentation.ui.viewModel

import com.neophron.account.domain.models.Account

data class HomeUiState(
    val account: Account? = null,
    val contentList: List<Content> = listOf(),
    val isContentLoading: Boolean = false
)