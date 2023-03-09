package com.neophron.network.products.source_impl.retrofit

import com.neophron.network.products.source.models.SearchResponse
import com.neophron.network.products.source_impl.retrofit.models.FlashSaleDataResponse
import com.neophron.network.products.source_impl.retrofit.models.LatestDataResponse
import retrofit2.http.GET

interface ProductsService {

    @GET("https://run.mocky.io/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatest(): LatestDataResponse

    @GET("https://run.mocky.io/v3/a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSale(): FlashSaleDataResponse


    @GET("https://run.mocky.io/v3/4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun searchProduct(): SearchResponse
}