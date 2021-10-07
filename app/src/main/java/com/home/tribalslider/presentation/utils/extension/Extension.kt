package com.home.tribalslider.presentation.utils.extension

import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_MIN
import com.home.tribalslider.presentation.utils.extension.Constants.AMOUNT_STEP
import java.text.DecimalFormat

fun Float.toPrice(): String {
    val pattern = "###,###.00"
    val decimalFormat = DecimalFormat(pattern)
    decimalFormat.groupingSize = 3

    return "GTQ " + decimalFormat.format(this)
}

fun Float.toPricePlus(): String {
    val pattern = "###,###.00"
    val decimalFormat = DecimalFormat(pattern)
    decimalFormat.groupingSize = 3

    return "GTQ +" + decimalFormat.format(this)
}

fun Float.validateMultipleV2(): Float {
    return AMOUNT_MIN + ( toInt() * AMOUNT_STEP )
}


