package com.justin.pocketmon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Author(
    val id: String = "",
    val name: String = "",
    val email: String = ""

) : Parcelable