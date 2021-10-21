package com.jumpstopstudios.zodiaque.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sign(
    val name: String,
    @DrawableRes val imageResId: Int,
    val datesRange: String
) : Parcelable
