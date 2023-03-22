package com.serdjuk.figmatest.app.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

const val API_LATEST = "https://run.mocky.io/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7"
const val API_SALE = "https://run.mocky.io/v3/a9ceeb6e-416d-4352-bde6-2203416576ac"
const val API_BRAND = "https://run.mocky.io/v3/3016c108-cc73-4e64-82bf-4508e54f0de1"
const val API_PRODUCT = "https://run.mocky.io/v3/748b68bb-9a12-42a4-b611-8d5dd2501409"
const val API_SEARCH = "https://run.mocky.io/v3/4c9cd822-9479-4509-803d-63197e5a9e19"

var productLatest: MutableState<List<Latest>?> = mutableStateOf(null)
var productSale: MutableState<List<FlashSale>?> = mutableStateOf(null)
val brands: MutableState<List<Brand>?> = mutableStateOf(null)
val product: MutableState<Product?> = mutableStateOf(null)
val searchResult = mutableStateOf<List<List<String>>?>(null)

object Api {
    private val client = OkHttpClient()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val coroutine = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)

    fun getProducts() {
        coroutine.launch {
            productLatest.value =
                getObject<LatestModel>(API_LATEST, LatestModel::class.java)?.latest
            productSale.value = getObject<SaleModel>(API_SALE, SaleModel::class.java)?.flash_sale
        }
    }

    fun getBrands() {
        coroutine.launch {
            brands.value = getObject<BrandModel>(API_BRAND, BrandModel::class.java)?.brand
        }
    }

    suspend fun getSearching(template: String) = withContext(context = coroutine.coroutineContext) {
        getObject<BrandModel2>(API_SEARCH, BrandModel2::class.java)?.words
    }

    fun getOneProduct() {
        coroutine.launch {
            product.value =
                getObject<ProductModel>(API_PRODUCT, ProductModel::class.java)?.product?.get(0)
        }
    }

    private inline fun <reified T> getObject(urlPath: String, javaClass: Class<*>): T? {
        val urlBuilder: HttpUrl.Builder = urlPath.toHttpUrlOrNull()!!.newBuilder()
        val url: String = urlBuilder.build().toString()
        val request: Request = Request.Builder().url(url).build()
        val response: Response = client.newCall(request).execute()
        return gson.fromJson(response.body?.string(), javaClass) as T
    }
}
