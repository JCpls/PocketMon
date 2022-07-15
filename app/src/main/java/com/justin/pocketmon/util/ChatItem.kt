package com.justin.pocketmon.util

import com.justin.pocketmon.data.Chat
import java.util.*

sealed class ChatItem {
    abstract val createdTime: Date

    data class UserSide(val chat: Chat) : ChatItem() {
        override val createdTime : Date
            get() = chat.createdTime
    }

    data class OtherSide(val chat: Chat) : ChatItem() {
        override val createdTime : Date
            get() = chat.createdTime
    }
}