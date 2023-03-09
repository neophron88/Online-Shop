package com.neophron.home.presentation.models

data class MediumBlockDisplay(
    val id: Long,
    val title: String,
    val list: List<ProductDisplay>
) : BlockDisplay()