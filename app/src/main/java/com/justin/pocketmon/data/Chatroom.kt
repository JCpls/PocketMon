package com.justin.pocketmon.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Chatroom (
    var id: String = "",
    val ownerImage: String = "",
    val lastTalkMessage: String? = null,
    val lastTalkTime: Date? = null,
    val name: String = "",
    val member: List<String> = listOf(""),
) : Parcelable