package com.iamwee.tamboon.utils

import android.text.Editable
import android.text.TextWatcher

class DonationTextWatcher(val onAmountChanged: (amount: Long) -> Unit) : TextWatcher {

    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val amount = s.toString().toLongOrNull() ?: 0
        onAmountChanged(amount)

    }
}