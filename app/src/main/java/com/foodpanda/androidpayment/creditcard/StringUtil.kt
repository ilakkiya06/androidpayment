package com.foodpanda.paymentcard.creditcard


object StringUtil {
    private const val INDEX_NOT_FOUND = -1
    fun difference(str1: String?, str2: String?): String? {
        if (str1 == null) {
            return str2
        }
        if (str2 == null) {
            return str1
        }
        val at = indexOfDifference(str1, str2)
        return if (at == INDEX_NOT_FOUND) {
            ""
        } else str2.substring(at)
    }

    fun indexOfDifference(cs1: CharSequence?, cs2: CharSequence?): Int {
        if (cs1 === cs2) {
            return INDEX_NOT_FOUND
        }
        if (cs1 == null || cs2 == null) {
            return 0
        }
        var i: Int
        i = 0
        while (i < cs1.length && i < cs2.length) {
            if (cs1[i] != cs2[i]) {
                break
            }
            ++i
        }
        return if (i < cs2.length || i < cs1.length) {
            i
        } else INDEX_NOT_FOUND
    }
}