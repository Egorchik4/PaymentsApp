package com.example.authorizationapp.data.datasource.network

import com.example.authorizationapp.data.api.PaymentApi
import com.example.authorizationapp.data.models.LogInModel
import com.example.authorizationapp.data.models.PaymentModel
import com.example.authorizationapp.domain.entity.SignInEntity
import com.example.authorizationapp.domain.entity.TokenEntity
import javax.inject.Inject

class PaymentDataSourceImpl @Inject constructor(private val api: PaymentApi) : PaymentDataSource {

	override suspend fun signIn(signInEntity: SignInEntity): LogInModel =
		api.signIn(signInEntity)

	override suspend fun getPaymentList(tokenEntity: TokenEntity): PaymentModel =
		api.getPayments(token = tokenEntity.token)

}

interface PaymentDataSource {

	suspend fun signIn(signInEntity: SignInEntity): LogInModel

	suspend fun getPaymentList(tokenEntity: TokenEntity): PaymentModel
}