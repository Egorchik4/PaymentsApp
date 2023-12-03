package com.example.authorizationapp.data.datasource.retrofit

import com.example.authorizationapp.data.api.PaymentApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject

class RetrofitBuilder @Inject constructor() {

	private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
		.setLevel(HttpLoggingInterceptor.Level.BODY)
	private val client: OkHttpClient.Builder = OkHttpClient.Builder()
		.addInterceptor(interceptor)

	private val url: String = "https://easypay.world/api-test/"
	private val retrofit: Retrofit = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(url)
		.client(client.build())
		.build()

	val paymentApi: PaymentApi = retrofit.create(PaymentApi::class.java)
}

class StringDeserializer : JsonDeserializer<String> {

	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
		return json.toString()
	}
}