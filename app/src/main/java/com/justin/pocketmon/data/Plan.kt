package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    var id: String = "",
    var ownerId: String = "Justin Yang",
    var title: String = "",
    var description: List<String> = listOf(""),
    var degree: Long = 0L,
    var createdTime: Timestamp = Timestamp.now(),
    var image: String = "",
    var method: MutableList<PlanMethod> = mutableListOf()
//            MutableList<String> = mutableListOf("")
//             List<String> = listOf("")
//            MutableList<PlanMethod> = mutableListOf()
//     MutableList<String> = mutableListOf("點擊底下，規劃夢想計畫")

): Parcelable
