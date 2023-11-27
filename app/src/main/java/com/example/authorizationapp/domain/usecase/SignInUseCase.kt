package com.example.authorizationapp.domain.usecase

import com.example.authorizationapp.domain.entity.SignInEntity
import com.example.authorizationapp.domain.entity.TokenEntity
import com.example.authorizationapp.domain.repository.PaymentRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: PaymentRepository) {

	suspend operator fun invoke(signInEntity: SignInEntity): TokenEntity =
		repository.signIn(signInEntity)
}