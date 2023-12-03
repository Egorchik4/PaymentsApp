package com.example.authorizationapp.data.api

import com.example.authorizationapp.data.models.LogInModel
import com.example.authorizationapp.data.models.PaymentModel
import com.example.authorizationapp.domain.entity.SignInEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentApi {

	@POST("login")
	@Headers("app-key: 12345", "v: 1")
	suspend fun signIn(@Body signInEntity: SignInEntity): LogInModel

	@GET("payments")
	@Headers("app-key: 12345", "v: 1")
	suspend fun getPayments(@Header("token") token: String): PaymentModel
}