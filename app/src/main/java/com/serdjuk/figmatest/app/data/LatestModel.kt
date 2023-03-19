package com.serdjuk.figmatest.app.data

data class LatestModel(
    val latest: ArrayList<Latest>
)

data class Latest(
    override val category: String,
    override val image_url: String,
    override val name: String,
    override val price: String,
    override val discount: String?
) : ProductApp