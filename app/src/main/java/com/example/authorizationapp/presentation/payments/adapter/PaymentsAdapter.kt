package com.example.authorizationapp.presentation.payments.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.authorizationapp.domain.entity.PaymentEntity

class PaymentsAdapter() : ListAdapter<PaymentEntity, PaymentsViewHolder>(PaymentsDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		PaymentsViewHolder.from(parent)

	override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
		holder.bind(getItem(position))
	}
}

class PaymentsDiffCallback : DiffUtil.ItemCallback<PaymentEntity>() {

	override fun areItemsTheSame(oldItem: PaymentEntity, newItem: PaymentEntity): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: PaymentEntity, newItem: PaymentEntity): Boolean {
		return oldItem == newItem
	}
}