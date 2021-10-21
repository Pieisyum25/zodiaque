package com.jumpstopstudios.zodiaque.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Sign(
    val name: String,
    @DrawableRes val imageResId: Int,
    val datesRange: String
)
