package com.example.authorizationapp.presentation.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.authorizationapp.R
import com.example.authorizationapp.databinding.FragmentPaymentsBinding
import com.example.authorizationapp.presentation.login.LogInFragment
import com.example.authorizationapp.presentation.payments.adapter.PaymentsAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

	private lateinit var binding: FragmentPaymentsBinding
	private val viewModel: PaymentsViewModel by viewModels()
	private lateinit var adapter: PaymentsAdapter

	companion object {

		const val EMPTY = ""

		const val EXIT_RESULT = "EXIT_RESULT"
		const val EXIT_KEY = "EXIT_KEY"
		const val EXIT = true
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentPaymentsBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bindAdapter()
		setObservers()
		setListeners()
		viewModel.showPaymentsList(arguments?.getString(LogInFragment.API_KEY_BUNDLE))
	}

	private fun bindAdapter() {
		adapter = PaymentsAdapter()
		binding.recyclerView.adapter = adapter
	}

	private fun setObservers() {
		viewModel.paymentsLive.observe(viewLifecycleOwner) {
			when (it) {
				is PaymentsState.Initial,
				is PaymentsState.Loading            -> renderLoading()
				is PaymentsState.PaymentsSuccessGet -> renderContent(it)
				is PaymentsState.ErrorPaymentsGet   -> renderError(it)
				is PaymentsState.Exit               -> renderExit()
			}
		}
	}

	private fun renderLoading() {
		with(binding) {
			progressBar.visibility = View.VISIBLE
			recyclerView.visibility = View.GONE
		}
	}

	private fun renderContent(state: PaymentsState.PaymentsSuccessGet) {
		with(binding) {
			progressBar.visibility = View.GONE
			recyclerView.visibility = View.VISIBLE
		}
		adapter.submitList(state.paymentsList)
	}

	private fun renderError(state: PaymentsState.ErrorPaymentsGet) {
		with(binding) {
			progressBar.visibility = View.GONE
			recyclerView.visibility = View.VISIBLE

			showSnackBar(recyclerView, state.errorMessage)
		}
	}

	private fun renderExit() {
		val bundle = Bundle()
		bundle.putBoolean(EXIT_KEY, EXIT)
		parentFragmentManager.setFragmentResult(EXIT_RESULT, bundle)
		findNavController().popBackStack()
	}

	private fun setListeners() {
		with(binding) {
			swipeRefreshLayout.setOnRefreshListener {
				viewModel.showPaymentsList(arguments?.getString(LogInFragment.API_KEY_BUNDLE))
				swipeRefreshLayout.isRefreshing = false
			}
			buttonBack.setOnClickListener {
				viewModel.exit()
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
}