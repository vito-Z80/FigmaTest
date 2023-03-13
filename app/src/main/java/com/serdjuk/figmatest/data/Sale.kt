package com.serdjuk.figmatest.data

data class Sale(
    val flash_sale: ArrayList<FlashSale>
)

data class FlashSale(
    override val category: String,
    override val discount: Int,
    override val image_url: String,
    override val name: String,
    override val price: Double
) : ProductApp

interface ProductApp {
    val name:String
    val discount:Int?
    val category:String
    val image_url:String
    val price:Double


    fun update(){

    }
}