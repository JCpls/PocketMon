package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanMethod(
    var done: Boolean = false,
    var score: Int = 5,
    var todo: String = "",
    var method: MutableList<String> = mutableListOf("立即點擊底下，新增計畫")
//            MutableList<String> = mutableListOf("")
//             List<String> = listOf("")

): Parcelable
