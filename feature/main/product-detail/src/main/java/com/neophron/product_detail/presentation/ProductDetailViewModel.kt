package com.neophron.product_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import com.neophron.product_detail.domain.models.ProductDetailQuery
import com.neophron.product_detail.domain.result.ProductDetailResult
import com.neophron.product_detail.domain.usecases.GetProductDetailUseCase
import com.neophron.product_detail.presentation.helper.toStringRes
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ProductDetailViewModel @AssistedInject constructor(
    @Assisted private val productId: Long,
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ProductDetailUiState())
    val uiState: LiveData<ProductDetailUiState> get() = _uiState

    private val _uiEvent = MutableSingleUseData<ProductDetailUiEvent>()
    val uiEvent: SingleUseData<ProductDetailUiEvent> get() = _uiEvent

    init {
        getProductDetail()
    }

    private fun getProductDetail() = viewModelScope.launch {
        _uiState.value = ProductDetailUiState(isLoading = true)
        val result = getProductDetailUseCase(ProductDetailQuery(productId))
        if (result is ProductDetailResult.Success)
            _uiState.value = ProductDetailUiState(result.detail)
        else if (result is ProductDetailResult.Error) {
            _uiEvent.value = ProductDetailUiEvent.ToastMessage(result.type.toStringRes())
            _uiState.value = ProductDetailUiState(isLoading = false)
        }
    }

    fun refreshData() = getProductDetail()
}