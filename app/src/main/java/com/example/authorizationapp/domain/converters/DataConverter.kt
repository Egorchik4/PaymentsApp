package com.example.authorizationapp.domain.converters

import java.text.SimpleDateFormat
import java.util.Locale

object DataConverter {

	private const val NO_DATA = "No Data"

	fun convertDateToString(date: Long?): String {
		return if (date != null) {
			SimpleDateFormat("dd.MM.yyyy", Locale("ru")).format(date)
		} else {
			NO_DATA
		}
	}
}