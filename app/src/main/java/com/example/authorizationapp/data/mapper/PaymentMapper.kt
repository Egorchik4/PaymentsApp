package com.example.authorizationapp.data.mapper

import com.example.authorizationapp.data.models.LogInModel
import com.example.authorizationapp.data.models.PaymentListModel
import com.example.authorizationapp.data.models.PaymentModel
import com.example.authorizationapp.domain.converters.AmountConverter.convertAmount
import com.example.authorizationapp.domain.converters.DataConverter.convertDateToString
import com.example.authorizationapp.domain.entity.PaymentEntity
import com.example.authorizationapp.domain.entity.TokenEntity

fun LogInModel.toTokenEntity(): TokenEntity =
	TokenEntity(token = response?.token ?: "")

fun PaymentModel.toListPaymentEntity(): List<PaymentEntity> =
	response.map {
		it.toPaymentEntity()
	}

fun PaymentListModel.toPaymentEntity(): PaymentEntity =
	PaymentEntity(
		id = id?.toString() ?: "No Id",
		title = title ?: "No Title",
		amount = convertAmount(amount),
		created = convertDateToString(created)
	)