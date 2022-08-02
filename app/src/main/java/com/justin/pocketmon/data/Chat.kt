package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Chat (
    var id: String = "",
    var senderId: String = "",
    var createdTime: Date = Timestamp(0),
    var content: String = "",
    var image: String = "",
    var name: String = ""

) : Parcelable