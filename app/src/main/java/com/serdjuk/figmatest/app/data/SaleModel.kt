package com.serdjuk.figmatest.app.data

data class SaleModel(
    val flash_sale: ArrayList<FlashSale>
)

class FlashSale(
    override val category: String,
    override val discount: String?,
    override val image_url: String,
    override val name: String,
    override val price: String
) : ProductApp

interface ProductApp {
    val name:String
    val discount:String?
    val category:String
    val image_url:String
    val price:String

}