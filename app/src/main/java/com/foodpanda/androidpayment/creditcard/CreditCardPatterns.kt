package com.foodpanda.paymentcard.creditcard

import android.content.Context
import com.foodpanda.androidpayment.R
import com.foodpanda.paymentcard.creditcard.CreditCardAppCompactEditText.CreditCard

import java.util.*


class CreditCardPatterns(private val context: Context) :
    CreditCardAppCompactEditText.CreditCartEditTextInterface {
    override fun mapOfRegexStringAndImageResourceForCreditCardEditText(creditCardEditText: CreditCardAppCompactEditText?): List<CreditCard>? {
        val listOfPatterns: MutableList<CreditCard> =
            ArrayList()
        val visa = CreditCard(
            "^4[0-9]{12}(?:[0-9]{3})?$",
            context.resources.getDrawable(R.drawable.visa),
            CreditCardTypeEnum.VISA.cartType
        )
        val mastercard = CreditCard(
            "^5[1-5][0-9]{14}$",
            context.resources.getDrawable(R.drawable.master),
            CreditCardTypeEnum.MASTER_CARD.cartType
        )
        val amex = CreditCard(
            "^3[47][0-9]{13}$",
            context.resources.getDrawable(R.drawable.amex),
            CreditCardTypeEnum.AMERICAN_EXPRESS.cartType
        )

        val discover = CreditCard(
            "^6(?:011|5[0-9]{2})[0-9]{3,}$",
            context.resources.getDrawable(R.drawable.discover),
            CreditCardTypeEnum.DISCOVER.cartType
        )
        listOfPatterns.add(visa)
        listOfPatterns.add(mastercard)
        listOfPatterns.add(amex)
        listOfPatterns.add(discover)
        return listOfPatterns
    }

}