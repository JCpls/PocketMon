package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignInResult(
    val error: String? = null,
//    @Json(name = "data") val userSignIn: UserSignIn? = null
) : Parcelable
