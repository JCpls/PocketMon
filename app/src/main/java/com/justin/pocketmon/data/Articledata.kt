package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Articledata(
    var category: String = " ",
    var content: String = " ",
    var createdTime: Timestamp = Timestamp.now(),
    var title: String = " ",
    var uid: String = " ",
    var image: String = "",
    var email: String = "",
    var id: String = "",
    var name: String = "",
    var comment: MutableList<String> = mutableListOf(""),

):Parcelable
