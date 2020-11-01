package com.foodpanda.paymentcard.creditcard

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText


class CreditCardTextWatcher(
    private val mEditText: AppCompatEditText,
    private val mListener: TextWatcherListener
) : TextWatcher {

    interface TextWatcherListener {
        fun onTextChanged(view: AppCompatEditText?, text: String?)
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        mListener.onTextChanged(mEditText, s.toString())
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

}