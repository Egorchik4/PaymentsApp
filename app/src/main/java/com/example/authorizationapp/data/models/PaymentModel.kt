package com.example.authorizationapp.data.models

import com.example.authorizationapp.data.datasource.retrofit.StringDeserializer
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class PaymentModel(
	@SerializedName("success")
	var success: String?,
	@SerializedName("response")
	var response: List<PaymentListModel>
)

data class PaymentListModel(
	@SerializedName("id")
	var id: Int?,
	@SerializedName("title")
	var title: String?,
	@SerializedName("amount")
	@JsonAdapter(StringDeserializer::class)
	var amount: String?,
	@SerializedName("created")
	var created: Long?
)