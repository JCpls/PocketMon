package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Broadcast(
    val id: String = "",
    val from: String = "",
    val to: String = "",
    val title: String = "",
    val time: String = "",
    val type: Int = 2

): Parcelable