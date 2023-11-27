package com.example.authorizationapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.authorizationapp.data.api.PaymentApi
import com.example.authorizationapp.data.datasource.network.PaymentDataSource
import com.example.authorizationapp.data.datasource.network.PaymentDataSourceImpl
import com.example.authorizationapp.data.datasource.retrofit.RetrofitBuilder
import com.example.authorizationapp.data.repository.PaymentRepositoryImpl
import com.example.authorizationapp.domain.repository.PaymentRepository
import com.example.authorizationapp.domain.usecase.GetOfInSharedPreferencesUseCase
import com.example.authorizationapp.domain.usecase.GetPaymentListUseCase
import com.example.authorizationapp.domain.usecase.PutInSharedPreferencesUseCase
import com.example.authorizationapp.domain.usecase.SignInUseCase
import com.example.authorizationapp.presentation.login.LogInFragment.Companion.APP_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun providePaymentApi(): PaymentApi {
		return RetrofitBuilder().paymentApi
	}

	@Provides
	@Singleton
	fun providePaymentDataSource(api: PaymentApi): PaymentDataSource {
		return PaymentDataSourceImpl(api)
	}

	@Provides
	@Singleton
	fun provideWeatherRepository(paymentDataSource: PaymentDataSource): PaymentRepository {
		return PaymentRepositoryImpl(paymentDataSource)
	}

	@Provides
	@Singleton
	fun provideSignInUseCase(paymentRepository: PaymentRepository): SignInUseCase {
		return SignInUseCase(paymentRepository)
	}

	@Provides
	@Singleton
	fun provideGetPaymentListUseCase(paymentRepository: PaymentRepository): GetPaymentListUseCase {
		return GetPaymentListUseCase(paymentRepository)
	}

	@Provides
	@Singleton
	fun provideSharedPrefStorage(@ApplicationContext context: Context): SharedPreferences {
		return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
	}

	@Provides
	@Singleton
	fun providePutInSharedPreferences(sharedPreferences: SharedPreferences): PutInSharedPreferencesUseCase {
		return PutInSharedPreferencesUseCase(sharedPreferences)
	}

	@Provides
	@Singleton
	fun provideGetOfInSharedPreferences(sharedPreferences: SharedPreferences): GetOfInSharedPreferencesUseCase {
		return GetOfInSharedPreferencesUseCase(sharedPreferences)
	}
}