package com.neophron.home.presentation.models

data class BigBlockDisplay(
    val id: Long,
    val title: String,
    val list: List<ProductDisplay>
) : BlockDisplay()