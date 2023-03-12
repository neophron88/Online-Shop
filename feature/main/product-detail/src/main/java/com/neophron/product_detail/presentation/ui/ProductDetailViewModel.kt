package com.neophron.product_detail.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.mylibrary.map
import com.neophron.mylibrary.require
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import com.neophron.product_detail.domain.models.ProductDetail
import com.neophron.product_detail.domain.models.ProductDetailQuery
import com.neophron.product_detail.domain.result.ProductDetailResult
import com.neophron.product_detail.domain.usecases.GetProductDetailUseCase
import com.neophron.product_detail.presentation.helper.toStringRes
import com.neophron.product_detail.presentation.models.ProductImageDisplay
import com.neophron.product_detail.presentation.models.ProductInfoDisplay
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

    private var totalPrice = 0.0
    private var lastCheckedImageIndex = 0


    private fun getProductDetail() = viewModelScope.launch {
        _uiState.value = _uiState.value.require().copy(isLoading = true)
        val result = getProductDetailUseCase(ProductDetailQuery(productId))
        if (result is ProductDetailResult.Success)
            processSuccessResult(result.detail)
        else if (result is ProductDetailResult.Error) {
            _uiEvent.value = ProductDetailUiEvent.ToastMessage(result.type.toStringRes())
            _uiState.value = _uiState.value.require().copy(isLoading = false)
        }
    }

    private fun processSuccessResult(detail: ProductDetail) {
        val productInfo = detail.map {
            ProductInfoDisplay(name, description, rating, numberOfReviews, price)
        }
        val productImages = detail.imageUrls
            .plus(detail.imageUrls)
            .plus(detail.imageUrls)
            .mapIndexed { index, url ->
                ProductImageDisplay(url = url, isChecked = index == lastCheckedImageIndex)
            }

        _uiState.value = _uiState.value.require().copy(
            detailInfo = productInfo,
            colors = detail.colors,
            images = productImages,
            isLoading = false
        )

    }

    fun refreshData() = getProductDetail()

    fun increaseQuantity() {
        val price = _uiState.value?.detailInfo?.price ?: return
        totalPrice += price
        _uiState.value = _uiState.value.require().copy(totalPrice = totalPrice)
    }

    fun decreaseQuantity() {
        val price = _uiState.value?.detailInfo?.price ?: return
        if (totalPrice > 0) totalPrice -= price
        _uiState.value = _uiState.value.require().copy(totalPrice = totalPrice)
    }

    fun selectImage(checkedIndex: Int) {
        val images = _uiState.value?.images ?: return
        val newImages = images.mapIndexed { index, imageDisplay ->
            imageDisplay.copy(isChecked = index == checkedIndex)
        }
        lastCheckedImageIndex = checkedIndex
        _uiState.value = _uiState.value.require().copy(images = newImages)
    }


}