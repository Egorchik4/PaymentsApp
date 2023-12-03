package com.example.authorizationapp.data.repository

import com.example.authorizationapp.data.datasource.network.PaymentDataSource
import com.example.authorizationapp.data.mapper.toListPaymentEntity
import com.example.authorizationapp.data.mapper.toTokenEntity
import com.example.authorizationapp.domain.entity.PaymentEntity
import com.example.authorizationapp.domain.entity.SignInEntity
import com.example.authorizationapp.domain.entity.TokenEntity
import com.example.authorizationapp.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val dataSource: PaymentDataSource) : PaymentRepository {

	override suspend fun signIn(signInEntity: SignInEntity): TokenEntity =
		dataSource.signIn(signInEntity).toTokenEntity()

	override suspend fun getPaymentList(tokenEntity: TokenEntity): List<PaymentEntity> =
		dataSource.getPaymentList(tokenEntity).toListPaymentEntity()
}