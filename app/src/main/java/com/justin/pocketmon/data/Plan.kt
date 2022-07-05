package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    var id: String = "",
    var ownerId: String = "",
    var title: String = "",
    var description: List<String> = listOf(""),
    var degree: Long = 0L,
    var createdTime: Timestamp = Timestamp.now(),
    var image: String = "",
    var method: MutableList<String> = mutableListOf("立即點擊底下，新增計畫")
//            MutableList<String> = mutableListOf("")
//             List<String> = listOf("")

): Parcelable
