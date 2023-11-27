package com.example.authorizationapp.data.models

import com.google.gson.annotations.SerializedName

data class LogInModel(
	@SerializedName("success")
	var success: String?,
	@SerializedName("response")
	var response: TokenModel?
)

data class TokenModel(
	@SerializedName("token")
	var token: String?
)