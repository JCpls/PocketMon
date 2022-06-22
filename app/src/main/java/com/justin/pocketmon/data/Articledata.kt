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
    val image: String = " ",
    val email: String = "justinyang29@gmail.com",
    val id: String = "justinyang29",
    val name: String = "Justin",

):Parcelable
