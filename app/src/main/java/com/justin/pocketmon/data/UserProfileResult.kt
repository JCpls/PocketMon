package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfileResult(
    val error: String? = null,
) : Parcelable
