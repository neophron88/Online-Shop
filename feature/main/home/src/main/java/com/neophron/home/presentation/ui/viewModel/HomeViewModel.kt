@file:OptIn(ExperimentalCoroutinesApi::class)

package com.neophron.home.presentation.ui.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.neophron.account.domain.result.AccountResult
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.home.domain.models.ProductsGroup
import com.neophron.home.domain.models.SearchQuery
import com.neophron.home.domain.result.ProductResult
import com.neophron.home.domain.result.SearchResult
import com.neophron.home.domain.usecases.GetFlashSaleProductsUseCase
import com.neophron.home.domain.usecases.GetLatestProductsUseCase
import com.neophron.home.domain.usecases.GetSearchProductsUseCase
import com.neophron.home.presentation.helper.UiMapper
import com.neophron.home.presentation.helper.toStringRes
import com.neophron.home.presentation.models.BigBlockDisplay
import com.neophron.home.presentation.models.MediumBlockDisplay
import com.neophron.mylibrary.map
import com.neophron.mylibrary.require
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class HomeViewModel @AssistedInject constructor(
    private val uiMapper: UiMapper,
    private val getAccountUseCase: GetAccountUseCase,
    private val getLatestProductsUseCase: GetLatestProductsUseCase,
    private val getFlashSaleProductsUseCase: GetFlashSaleProductsUseCase,
    private val getSearchProductsUseCase: GetSearchProductsUseCase
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
        collectAccountData()
        collectUiHomeContent()
    }

    private fun collectAccountData() = viewModelScope.launch {
        getAccountUseCase().collect {
            if (it is AccountResult.Success)
                _uiState.value = requireUiState().copy(account = it.account)
        }
    }

    private fun collectUiHomeContent() =
        uiHomeContent.launchIn(viewModelScope)

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
            val latestData = latest.productsGroup
            val flashSaleData = flashSale.productsGroup
            if(latestData.products.isEmpty() || flashSaleData.products.isEmpty()) return
            _uiState.value = requireUiState().copy(
                contentList = listOf(
                    latestData.map {
                        MediumBlockDisplay(id, title, uiMapper.mapToDisplay(products))
                    },
                    flashSaleData.map {
                        BigBlockDisplay(id, title, uiMapper.mapToDisplay(products))
                    },
                    generateBrandsBlock(latestData, flashSaleData)
                ),
                isContentLoading = false
            )
        }
    }

    private fun requireUiState() = _uiState.value.require()

    //This is a very bad code generates brands block because, there is no api endpoint for this
    private suspend fun generateBrandsBlock(var1: ProductsGroup, var2: ProductsGroup) =
        MediumBlockDisplay(
            3L, "Brands", uiMapper.mapToDisplay((var1.products + var2.products).shuffled())
        )

    fun refreshContent() {
        refresh.value = Unit
    }

    fun searchProducts(search: String) = viewModelScope.launch {
        val result = getSearchProductsUseCase(SearchQuery(search))
        if (result is SearchResult.Success)
            _uiEvent.value = HomeUiEvent.SearchingResult(result.data)
    }

    fun resetSearchProducts() {
        _uiEvent.value = HomeUiEvent.SearchingResult(emptyList())
    }

}