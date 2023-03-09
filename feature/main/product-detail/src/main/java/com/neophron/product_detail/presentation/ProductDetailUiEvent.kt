package com.neophron.product_detail.presentation

import androidx.annotation.StringRes

sealed class ProductDetailUiEvent {
    class ToastMessage(@StringRes val msgRes: Int) : ProductDetailUiEvent()
}