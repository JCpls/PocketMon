package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatRoom(
    val id: String = "",
    val memberIds: String = "",
    val chats: String = "",

): Parcelable


@Parcelize
data class chats(
    val id: String = "",
    val sender: String = "",
    val time: String = ""
): Parcelable