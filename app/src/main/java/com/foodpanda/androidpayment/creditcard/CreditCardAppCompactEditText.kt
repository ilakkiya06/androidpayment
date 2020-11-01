package com.foodpanda.paymentcard.creditcard

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.foodpanda.androidpayment.R


class CreditCardAppCompactEditText : AppCompatEditText,
    CreditCardTextWatcher.TextWatcherListener {
    /**
     * List of CreditCard objects containing the image to display
     * and the regex for pattern matching.
     */
    private var mListOfCreditCardChecks: List<CreditCard>? =
        null

    /**
     * This drawable is shown by default and when no match is found
     */
    private var mNoMatchFoundDrawable: Drawable? = null
    private var mCurrentCreditCardMatch: CreditCard? =
        null
    private var mCreditCardEditTextInterface: CreditCartEditTextInterface? =
        null
    private var mTextWatcher: CreditCardTextWatcher? = null
    private var mCreditCardPatterns: CreditCardPatterns? = null

    private var mSeparator: String? = null
    var minimumCreditCardLength = 0
    var maximumCreditCardLength = 0
    private var mPreviousText: String? = null

    interface CreditCartEditTextInterface {
        fun mapOfRegexStringAndImageResourceForCreditCardEditText(creditCardEditText: CreditCardAppCompactEditText?): List<CreditCard>?
    }

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
        initPropertiesFromAttributes(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        init()
        initPropertiesFromAttributes(context, attrs)
    }

    var noMatchFoundDrawable: Drawable?
        get() = mNoMatchFoundDrawable
        set(noMatchFoundDrawable) {
            if (noMatchFoundDrawable != null) {
                mNoMatchFoundDrawable = noMatchFoundDrawable
                mNoMatchFoundDrawable!!.setBounds(
                    0,
                    0,
                    mNoMatchFoundDrawable!!.intrinsicWidth,
                    mNoMatchFoundDrawable!!.intrinsicHeight
                )
                showRightDrawable(null)
            }
        }

    fun setCreditCardEditTextListener(creditCartEditTextInterface: CreditCartEditTextInterface?) {
        mCreditCardEditTextInterface = creditCartEditTextInterface
        if (mCreditCardEditTextInterface != null) {
            mListOfCreditCardChecks =
                mCreditCardEditTextInterface!!.mapOfRegexStringAndImageResourceForCreditCardEditText(
                    this
                )
        }
    }


    override fun onTextChanged(view: AppCompatEditText?, text: String?) {
        matchRegexPatternsWithText(text!!.replace(mSeparator!!, ""))
        if (mPreviousText != null && text.length > mPreviousText!!.length) {
            val difference = StringUtil.difference(text, mPreviousText)
            if (difference != mSeparator) {
                addSeparatorToText()
            }
        }
        mPreviousText = text
    }

    class CreditCard(
        regexPattern: String?,
        drawable: Drawable?,
        type: String?
    ) {
        val regexPattern: String
        val drawable: Drawable
        val type: String

        init {
            require(!(regexPattern == null || drawable == null || type == null))
            this.regexPattern = regexPattern
            this.drawable = drawable
            this.type = type
        }
    }

    private fun initPropertiesFromAttributes(
        context: Context,
        attrs: AttributeSet?
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CreditCardEditText)
        if (typedArray != null) {
            mSeparator = typedArray.getString(R.styleable.CreditCardEditText_separator)
            if (mSeparator == null) {
            }
            typedArray.recycle()
        }
    }

    private fun init() {
        minimumCreditCardLength = MINIMUM_CREDIT_CARD_LENGTH
        maximumCreditCardLength = MAXIMUM_CREDIT_CARD_LENGTH
        mNoMatchFoundDrawable =
            resources.getDrawable(DEFAULT_NO_MATCH_FOUND_DRAWABLE)
        mNoMatchFoundDrawable?.setBounds(
            0,
            0,
            mNoMatchFoundDrawable!!.getIntrinsicWidth(),
            mNoMatchFoundDrawable!!.getIntrinsicHeight()
        )
        inputType = InputType.TYPE_CLASS_NUMBER
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            mNoMatchFoundDrawable,
            compoundDrawables[3]
        )
        if (mCreditCardEditTextInterface != null) {
            mListOfCreditCardChecks =
                mCreditCardEditTextInterface!!.mapOfRegexStringAndImageResourceForCreditCardEditText(
                    this
                )
        }
        mTextWatcher = CreditCardTextWatcher(this, this)
        mCreditCardPatterns = CreditCardPatterns(context)
        addTextChangedListener(mTextWatcher)
         setCreditCardEditTextListener(mCreditCardPatterns);
    }

    private fun addSeparatorToText() {
        var text = text.toString()
        text = text.replace(mSeparator!!, "")
        if (text.length >= 16) {
            return
        }
        val interval = 4
        val separator = mSeparator!![0]
        val stringBuilder = StringBuilder(text)
        for (i in 0 until text.length / interval) {
            stringBuilder.insert((i + 1) * interval + i, separator)
        }
        removeTextChangedListener(mTextWatcher)
        setText(stringBuilder.toString())
        setSelection(getText()!!.length)
        addTextChangedListener(mTextWatcher)
    }

    private fun matchRegexPatternsWithText(text: String) {
        if (mListOfCreditCardChecks != null && mListOfCreditCardChecks!!.size > 0) {
            var drawable: Drawable? = null
            for (creditCard in mListOfCreditCardChecks!!) {
                val regex = creditCard.regexPattern.toRegex()
                if (text.matches(regex)) {
                    mCurrentCreditCardMatch = creditCard
                    drawable = creditCard.drawable
                    break
                }
            }
            showRightDrawable(drawable)
        }
    }

    private fun showRightDrawable(drawable: Drawable?) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                drawable,
                compoundDrawables[3]
            )
        } else {
            mCurrentCreditCardMatch = null
            setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                mNoMatchFoundDrawable,
                compoundDrawables[3]
            )
        }
    }

    companion object {
        private const val DEFAULT_NO_MATCH_FOUND_DRAWABLE =
            R.drawable.credit_card

        /**
         * Default minimum and maximum card length.
         */
        private const val MINIMUM_CREDIT_CARD_LENGTH = 13
        private const val MAXIMUM_CREDIT_CARD_LENGTH = 19
    }


}


