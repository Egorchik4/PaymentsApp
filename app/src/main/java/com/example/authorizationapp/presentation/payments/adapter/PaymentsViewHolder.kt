package com.example.authorizationapp.presentation.payments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.authorizationapp.databinding.PaymentItemBinding
import com.example.authorizationapp.domain.entity.PaymentEntity

class PaymentsViewHolder(
	private val binding: PaymentItemBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): PaymentsViewHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = PaymentItemBinding.inflate(inflater, parent, false)
			return PaymentsViewHolder(binding)
		}
	}

	fun bind(
		paymentEntity: PaymentEntity
	) {
		with(binding) {
			textId.text = paymentEntity.id
			textTitle.text = paymentEntity.title
			textAmount.text = paymentEntity.amount
			textCreated.text = paymentEntity.created
		}
	}
}