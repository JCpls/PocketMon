package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article(
    var id: String = "",
    var tag: String = "",
    var createdTime: Long = -1,
    var title: String = "",
    var content: String = "",
    val author: Author? = null
) : Parcelable