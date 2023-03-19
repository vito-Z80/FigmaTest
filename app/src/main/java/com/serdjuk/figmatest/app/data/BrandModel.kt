package com.serdjuk.figmatest.app.data

data class BrandModel(
    val brand: List<Brand>
)

data class Brand(
    val image_url: String,
    val name: String
)