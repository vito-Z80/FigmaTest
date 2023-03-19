package com.serdjuk.figmatest.app.data

data class ProductModel(
    val product: List<Product>
)

data class Product(
    val category: String,
    val colors: List<String>,
    val description: String,
    val discount: Int,
    val images: List<String>,
    val name: String,
    val price: Double,
    val rate: Double,
    val reviews: Int
)