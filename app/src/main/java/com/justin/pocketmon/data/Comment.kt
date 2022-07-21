package com.justin.pocketmon.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    var authorId: String = "",
    var authorImage: String = "",
    var authorName: String = "",
    var content: String = "",
    var articleId: String = "",
    var id: String = "",
    var createdTime: Timestamp = Timestamp.now()

    ): Parcelable