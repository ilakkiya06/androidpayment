package com.foodpanda.paymentcard.creditcard


enum class CreditCardTypeEnum(
   val cartType: String
) {
    VISA("Visa"), MASTER_CARD("MasterCard"), AMERICAN_EXPRESS("American Express"), DINERS("Diners"), DISCOVER(
        "Discover"
    );

    companion object {
        fun creditCardTypes(): Array<String?> {
            val types =
                arrayOfNulls<String>(values().size)
            var i = 0
            for (type in values()) {
                types[i] = type.cartType
                i += 1
            }
            return types
        }
    }

}