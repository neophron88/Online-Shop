package com.neophron.network.product_detail.source_impl

import com.neophron.network.product_detail.source.models.ProductDetailResponse
import retrofit2.http.GET

interface ProductDetailService {

    @GET(" https://run.mocky.io/v3/f7f99d04-4971-45d5-92e0-70333383c239/")
    suspend fun loadProductDetail(): ProductDetailResponse

}