package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignIn(
//    @Json(name = "access_token") val accessToken: String,
//    @Json(name = "access_expired") val accessExpired: Int,
    val user: User
) : Parcelable
