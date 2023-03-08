package com.neophron.home.presentation.ui.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.neophron.account.domain.result.AccountResult
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.home.domain.models.ProductsGroup
import com.neophron.home.domain.result.ProductResult
import com.neophron.home.domain.usecases.GetFlashSaleProductsUseCase
import com.neophron.home.domain.usecases.GetLatestProductsUseCase
import com.neophron.home.presentation.helper.UiMapper
import com.neophron.home.presentation.helper.toStringRes
import com.neophron.home.presentation.models.BigBlockDisplay
import com.neophron.home.presentation.models.MediumBlockDisplay
import com.neophron.mylibrary.map
import com.neophron.mylibrary.require
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val uiMapper: UiMapper,
    private val getAccountUseCase: GetAccountUseCase,
    private val getLatestProductsUseCase: GetLatestProductsUseCase,
    private val getFlashSaleProductsUseCase: GetFlashSaleProductsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(HomeUiState())
    val uiState: LiveData<HomeUiState> get() = _uiState

    private val _uiEvent = MutableSingleUseData<HomeUiEvent>()
    val uiEvent: SingleUseData<HomeUiEvent> get() = _uiEvent

    private val refresh = MutableLiveData(Unit)

    private val uiHomeContent = refresh.asFlow()
        .flatMapLatest {
            combine(
                getLatestProductsUseCase(),
                getFlashSaleProductsUseCase(),
                ::processResults
            )
        }

    init {
        collectUiHomeContent()
        collectAccountData()
    }

    private fun collectUiHomeContent() =
        uiHomeContent.launchIn(viewModelScope)

    private fun collectAccountData() = viewModelScope.launch {
        getAccountUseCase().collect {
            if (it is AccountResult.Success)
                _uiState.value = requireUiState().copy(account = it.account)
        }
    }

    private suspend fun processResults(latest: ProductResult, flashSale: ProductResult) {
        if (isProcessedAnyPending(latest, flashSale)) return
        if (isProcessedAnyError(latest, flashSale)) return
        isProcessedBothSuccess(latest, flashSale)
    }

    private fun isProcessedAnyPending(
        latest: ProductResult,
        flashSale: ProductResult
    ): Boolean {
        val isAnyPending = latest is ProductResult.Pending || flashSale is ProductResult.Pending
        if (isAnyPending) _uiState.value = requireUiState().copy(isContentLoading = true)
        return isAnyPending
    }


    private fun isProcessedAnyError(
        latest: ProductResult,
        flashSale: ProductResult
    ): Boolean {
        @StringRes var errMsg: Int? = null
        if (latest is ProductResult.Error) errMsg = latest.type.toStringRes()
        if (flashSale is ProductResult.Error) errMsg = flashSale.type.toStringRes()

        if (errMsg != null) {
            _uiEvent.value = HomeUiEvent.ToastMessage(errMsg)
            _uiState.value = requireUiState().copy(isContentLoading = false)
        }
        val isProcessed = errMsg != null
        return isProcessed
    }

    private suspend fun isProcessedBothSuccess(
        latest: ProductResult,
        flashSale: ProductResult
    ) {
        if (latest is ProductResult.Success && flashSale is ProductResult.Success) {
            _uiState.value = requireUiState().copy(
                contentList = listOf(
                    latest.productsGroup.map {
                        MediumBlockDisplay(title, uiMapper.mapToDisplay(products))
                    },
                    flashSale.productsGroup.map {
                        BigBlockDisplay(title, uiMapper.mapToDisplay(products))
                    },
                    generateBrandsBlock(latest.productsGroup, latest.productsGroup)
                ),
                isContentLoading = false
            )
        }
    }

    private fun requireUiState() = _uiState.value.require()

    //This is bad code
    private suspend fun generateBrandsBlock(var1: ProductsGroup, var2: ProductsGroup) =
        MediumBlockDisplay(
            "Brands", uiMapper.mapToDisplay((var1.products + var2.products).shuffled())
        )

    fun refreshData() {
        refresh.value = Unit
    }
}