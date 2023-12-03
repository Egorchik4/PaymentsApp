package com.example.authorizationapp.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.authorizationapp.R
import com.example.authorizationapp.databinding.FragmentLogInBinding
import com.example.authorizationapp.presentation.payments.PaymentsFragment.Companion.EXIT_KEY
import com.example.authorizationapp.presentation.payments.PaymentsFragment.Companion.EXIT_RESULT
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment() {

	private lateinit var binding: FragmentLogInBinding
	private val viewModel: LogInViewModel by viewModels()

	companion object {

		const val EMPTY_FIELD = "Required Field!"
		const val INCORRECT_LOGIN_OR_PASSWORD = "Incorrect login or password"

		const val API_KEY_BUNDLE = "API_KEY_BUNDLE"
		const val APP_PREFERENCES = "APP_PREFERENCES"
		const val KEY_SHARED_PREFERENCES = "KEY_SHARED_PREFERENCES"
		const val API_PROBLEM = "Api problem"
		const val NOT_FOUND = "Not found"
		const val FLAGS = 0
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentLogInBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setListeners()
	}

	override fun onStart() {
		super.onStart()
		parentFragmentManager.setFragmentResultListener(EXIT_RESULT, viewLifecycleOwner) { _, data ->
			if (data.getBoolean(EXIT_KEY)) {
				viewModel.initial()
			}
		}
	}

	private fun setObservers() {
		viewModel.validationLive.observe(viewLifecycleOwner) {
			when (it) {
				is LogInState.Initial         -> renderInitial()
				is LogInState.CorrectValid    -> renderCorrect(it)
				is LogInState.ErrorValid      -> renderError(it)
				is LogInState.Loading         -> renderLoading()
				is LogInState.TokenSuccessGet -> renderTokenSuccess(it)
				is LogInState.ErrorTokenGet   -> renderTokenError(it)
			}
		}
	}

	private fun renderInitial() {
		with(binding) {
			progressBar.visibility = View.GONE
			buttonLogIn.visibility = View.VISIBLE
		}
	}

	private fun renderCorrect(state: LogInState.CorrectValid) {
		when (state.detailCorrect) {
			is DetailCorrect.CorrectLogin    -> {
				binding.editTextLayoutLogin.isErrorEnabled = false
			}

			is DetailCorrect.CorrectPassword -> {
				binding.editTextLayoutPassword.isErrorEnabled = false
			}
		}
	}

	private fun renderError(state: LogInState.ErrorValid) {
		when (state.detailError) {
			is DetailError.ErrorLogin    -> {
				binding.editTextLayoutLogin.error = state.detailError.message
			}

			is DetailError.ErrorPassword -> {
				binding.editTextLayoutPassword.error = state.detailError.message
			}
		}
	}

	private fun renderLoading() {
		with(binding) {
			progressBar.visibility = View.VISIBLE
			buttonLogIn.visibility = View.GONE
		}
	}

	private fun renderTokenSuccess(content: LogInState.TokenSuccessGet) {
		val bundle = Bundle()
		bundle.putString(API_KEY_BUNDLE, content.token)
		findNavController().navigate(R.id.action_logInFragment_to_paymentsFragment, bundle)
	}

	private fun renderTokenError(error: LogInState.ErrorTokenGet) {
		with(binding) {
			progressBar.visibility = View.GONE
			buttonLogIn.visibility = View.VISIBLE
			showSnackBar(binding.buttonLogIn, error.errorMessage)
		}
	}

	private fun setListeners() {
		with(binding) {
			editTextLogin.addTextChangedListener {
				viewModel.validateLogin(it.toString())
			}
			editTextPassword.addTextChangedListener {
				viewModel.validatePassword(it.toString())
			}
			buttonLogIn.setOnClickListener {
				hideKeyboard(it)
				viewModel.signIn(
					editTextLogin.text.toString(),
					editTextPassword.text.toString()
				)
			}
		}
	}

	private fun showSnackBar(view: View, message: String) {
		context?.let {
			Snackbar.make(view, message, Snackbar.LENGTH_LONG)
				.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
				.setBackgroundTint(it.getColor(R.color.main_color))
				.show()
		}
	}

	private fun hideKeyboard(view: View) {
		val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
		imm?.hideSoftInputFromWindow(view.windowToken, FLAGS)
	}
}