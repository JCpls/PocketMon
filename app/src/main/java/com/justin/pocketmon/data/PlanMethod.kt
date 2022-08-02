package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanMethod(
    var done: Boolean = false,
    var score: String = "",
    var todo: String = "",

): Parcelable
