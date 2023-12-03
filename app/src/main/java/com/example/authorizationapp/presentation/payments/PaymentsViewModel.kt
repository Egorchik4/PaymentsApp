package com.example.authorizationapp.presentation.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorizationapp.domain.entity.TokenEntity
import com.example.authorizationapp.domain.usecase.GetPaymentListUseCase
import com.example.authorizationapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.authorizationapp.presentation.login.LogInFragment
import com.example.authorizationapp.presentation.payments.PaymentsFragment.Companion.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
	private val getPaymentListUseCase: GetPaymentListUseCase,
	private val putInSharedPreferencesUseCase: PutInSharedPreferencesUseCase
) : ViewModel() {

	private val _paymentsMut = MutableLiveData<PaymentsState>(PaymentsState.Initial)
	val paymentsLive: LiveData<PaymentsState> = _paymentsMut

	fun showPaymentsList(token: String?) {
		if (!token.isNullOrEmpty()) {
			_paymentsMut.value = PaymentsState.Loading
			viewModelScope.launch {
				try {
					val paymentList = getPaymentListUseCase(TokenEntity(token = token))
					_paymentsMut.value = PaymentsState.PaymentsSuccessGet(paymentList)
				} catch (e: HttpException) {
					if (e.code() == 403) {
						_paymentsMut.value = PaymentsState.ErrorPaymentsGet(errorMessage = LogInFragment.API_PROBLEM)
					} else if (e.code() == 404) {
						_paymentsMut.value = PaymentsState.ErrorPaymentsGet(errorMessage = LogInFragment.NOT_FOUND)
					} else {
						_paymentsMut.value = PaymentsState.ErrorPaymentsGet(errorMessage = e.code().toString())
					}
				} catch (e: IOException) {
					_paymentsMut.value = PaymentsState.ErrorPaymentsGet(errorMessage = e.message.toString())
				}
			}
		}
	}

	fun exit() {
		putInSharedPreferencesUseCase(key = LogInFragment.KEY_SHARED_PREFERENCES, data = EMPTY)
		_paymentsMut.value = PaymentsState.Exit
	}
}