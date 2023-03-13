package com.serdjuk.figmatest.data

data class Latest(
    val latest: ArrayList<LatestX>
)

data class LatestX(
    override val category: String,
    override val image_url: String,
    override val name: String,
    override val price: Double,
    override val discount: Int? = null
) : ProductApp