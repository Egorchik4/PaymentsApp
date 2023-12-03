package com.example.authorizationapp.presentation.payments

import com.example.authorizationapp.domain.entity.PaymentEntity

sealed class PaymentsState {

	object Initial : PaymentsState()

	object Loading : PaymentsState()

	data class PaymentsSuccessGet(var paymentsList: List<PaymentEntity>) : PaymentsState()

	data class ErrorPaymentsGet(val errorMessage: String) : PaymentsState()

	object Exit : PaymentsState()
}