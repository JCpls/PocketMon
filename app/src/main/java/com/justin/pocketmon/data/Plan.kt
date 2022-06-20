package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    var id: String = "",
    var ownerId: String = "",
    var title: String = "",
    var description: String = "",
    var degree: Long = 2,
    var createdTime: Long = -1,
    val user: User? = null

): Parcelable