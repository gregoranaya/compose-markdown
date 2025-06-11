package com.bcp.com.markdown

import android.util.TypedValue

/**
 * Requires API Level 26 to apply auto size
 * */
data class BCPAutoSizeConfig(
    val autoSizeMinTextSize: Int,
    val autoSizeMaxTextSize: Int,
    val autoSizeStepGranularity: Int,
    val unit: Int = TypedValue.COMPLEX_UNIT_SP,
)
