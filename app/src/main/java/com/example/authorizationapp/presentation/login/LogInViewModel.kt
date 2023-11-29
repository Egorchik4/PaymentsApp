package com.example.authorizationapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorizationapp.domain.entity.SignInEntity
import com.example.authorizationapp.domain.usecase.GetOfInSharedPreferencesUseCase
import com.example.authorizationapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.authorizationapp.domain.usecase.SignInUseCase
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.API_PROBLEM
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.EMPTY_FIELD
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.INCORRECT_LOGIN_OR_PASSWORD
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.KEY_SHARED_PREFERENCES
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.NOT_FOUND
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
	private val signInUseCase: SignInUseCase,
	private val putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase,
	private val getOfInSharedPreferencesUseCase: GetOfInSharedPreferencesUseCase
) : ViewModel() {

	private val _validationMut = MutableLiveData<LogInState>(LogInState.Initial)
	val validationLive: LiveData<LogInState> = _validationMut

	fun validateLogin(text: String): Boolean {
		return if (text.trim().isEmpty()) {
			_validationMut.value = LogInState.ErrorValid(detailError = DetailError.ErrorLogin(EMPTY_FIELD))
			false
		} else {
			_validationMut.value = LogInState.CorrectValid(DetailCorrect.CorrectLogin)
			true
		}
	}

	fun validatePassword(text: String): Boolean {
		return if (text.trim().isEmpty()) {
			_validationMut.value = LogInState.ErrorValid(detailError = DetailError.ErrorPassword(EMPTY_FIELD))
			false
		} else {
			_validationMut.value = LogInState.CorrectValid(DetailCorrect.CorrectPassword)
			true
		}
	}

	fun signIn(loginText: String, passwordText: String) {
		if (validateLogin(loginText) && validatePassword(passwordText)) {
			_validationMut.value = LogInState.Loading
			viewModelScope.launch {
				try {
					val tokenEntity = signInUseCase(SignInEntity(login = loginText, password = passwordText))
					if (tokenEntity.token.isNotEmpty()) {
						_validationMut.value = LogInState.TokenSuccessGet(token = tokenEntity.token)
						putInStorage(tokenEntity.token)
					} else {
						_validationMut.value = LogInState.ErrorTokenGet(errorMessage = INCORRECT_LOGIN_OR_PASSWORD)
					}
				} catch (e: HttpException) {
					if (e.code() == 403) {
						_validationMut.value = LogInState.ErrorTokenGet(errorMessage = API_PROBLEM)
					} else if (e.code() == 404) {
						_validationMut.value = LogInState.ErrorTokenGet(errorMessage = NOT_FOUND)
					} else {
						_validationMut.value = LogInState.ErrorTokenGet(errorMessage = e.code().toString())
					}
				} catch (e: IOException) {
					_validationMut.value = LogInState.ErrorTokenGet(errorMessage = e.message.toString())
				}
			}
		}
	}

	private fun putInStorage(token: String) {
		putInSharedPreferencesUseCase(KEY_SHARED_PREFERENCES, token)
	}

	private fun getOfInStorage() {
		val token = getOfInSharedPreferencesUseCase(KEY_SHARED_PREFERENCES)
		if (!token.isNullOrEmpty()) {
			_validationMut.value = LogInState.TokenSuccessGet(token = token)
		}
	}

	fun initial() {
		_validationMut.value = LogInState.Initial
	}

	init {
		getOfInStorage()
	}
}