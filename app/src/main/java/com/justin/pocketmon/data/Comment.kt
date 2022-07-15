package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    var authorId: String = "",
    var authorImage: String = "",
    var content: String = "",
    var articleId: String = "",
    var id: String

): Parcelable