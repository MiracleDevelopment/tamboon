package com.iamwee.tamboon.utils

import java.text.DecimalFormat

object CurrencyFormatter {

    fun format(amount: Long): String {
        val formatter = DecimalFormat("#,###,###,###")
        return formatter.format(amount)
    }
}