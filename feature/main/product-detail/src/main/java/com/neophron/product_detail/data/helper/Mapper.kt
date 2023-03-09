package com.neophron.product_detail.data.helper

import com.neophron.network.product_detail.source.models.ProductDetailResponse
import com.neophron.product_detail.domain.models.ProductDetail

fun ProductDetailResponse.mapToProductDetail() =
    ProductDetail(name, description, rating, numberOfReviews, price, colors, imageUrls)


