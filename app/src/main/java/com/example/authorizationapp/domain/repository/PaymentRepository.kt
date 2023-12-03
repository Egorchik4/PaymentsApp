package com.example.authorizationapp.domain.repository

import com.example.authorizationapp.domain.entity.PaymentEntity
import com.example.authorizationapp.domain.entity.SignInEntity
import com.example.authorizationapp.domain.entity.TokenEntity

interface PaymentRepository {

	suspend fun signIn(signInEntity: SignInEntity): TokenEntity

	suspend fun getPaymentList(tokenEntity: TokenEntity): List<PaymentEntity>
}