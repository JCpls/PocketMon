package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var friend: String = "",
    var image: String = "",
    var userToken: String = ""

): Parcelable