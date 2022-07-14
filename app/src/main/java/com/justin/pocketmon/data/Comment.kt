package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: String = "",
    val senderId: String = "",
    val content: String = "",
    val articleId: String = "",

): Parcelable