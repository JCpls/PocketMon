package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Broadcast(
    var id: String = "",
    var from: String = "",
    var title: String = "",
    var timeFinish: Timestamp = Timestamp.now(),
    var timeStart: String = ""

): Parcelable