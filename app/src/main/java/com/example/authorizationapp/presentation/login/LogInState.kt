package com.example.authorizationapp.presentation.login

sealed class LogInState {

	object Initial : LogInState()

	data class CorrectValid(val detailCorrect: DetailCorrect) : LogInState()

	data class ErrorValid(val detailError: DetailError) : LogInState()

	object Loading : LogInState()

	data class TokenSuccessGet(val token: String) : LogInState()

	data class ErrorTokenGet(val errorMessage: String) : LogInState()
}

sealed class DetailCorrect {
	object CorrectLogin : DetailCorrect()
	object CorrectPassword : DetailCorrect()
}

sealed class DetailError {
	data class ErrorLogin(val message: String) : DetailError()
	data class ErrorPassword(val message: String) : DetailError()
}