package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize


@Parcelize
data class ToDo(

    var id: String = "",
    var ownerId: String = "",
    var planId: String = "",
    var method: String = "點擊底下，執行夢想計畫",
    var createdTime: Timestamp = Timestamp.now(),

): Parcelable
