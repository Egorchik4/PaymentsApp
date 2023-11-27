package com.example.authorizationapp.domain.converters

object AmountConverter {

	private const val NO_AMOUNT = "No Amount"

	fun convertAmount(amount: String?): String {
		return if (!amount.isNullOrEmpty()) {
			val stringBuilder = StringBuilder()
			amount.toCharArray().forEach {
				if (it.toString() != "\"") {
					stringBuilder.append(it)
				}
			}
			if (stringBuilder.isEmpty()) {
				NO_AMOUNT
			} else {
				stringBuilder.toString()
			}
		} else {
			NO_AMOUNT
		}
	}
}