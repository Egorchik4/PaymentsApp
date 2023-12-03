package com.example.authorizationapp.domain.usecase

import com.example.authorizationapp.domain.entity.PaymentEntity
import com.example.authorizationapp.domain.entity.TokenEntity
import com.example.authorizationapp.domain.repository.PaymentRepository
import javax.inject.Inject

class GetPaymentListUseCase @Inject constructor(private val repository: PaymentRepository) {

	suspend operator fun invoke(tokenEntity: TokenEntity): List<PaymentEntity> =
		repository.getPaymentList(tokenEntity)
}