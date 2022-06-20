package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val image: String = "",
    val description: String = "",
    val degree: Long = 2

): Parcelable