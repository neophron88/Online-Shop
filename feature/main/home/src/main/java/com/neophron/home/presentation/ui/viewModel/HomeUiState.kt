package com.neophron.home.presentation.ui.viewModel

import com.neophron.account.domain.models.Account
import com.neophron.home.presentation.models.BlockDisplay

data class HomeUiState(
    val account: Account? = null,
    val contentList: List<BlockDisplay> = listOf(),
    val isContentLoading: Boolean = false
)