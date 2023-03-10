package com.neophron.product_detail.presentation.ui

import androidx.annotation.StringRes

sealed class ProductDetailUiEvent {
    class ToastMessage(@StringRes val msgRes: Int) : ProductDetailUiEvent()
}