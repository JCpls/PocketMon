package com.justin.pocketmon.data

import com.google.firebase.Timestamp

data class Articledata(
    var category: String = " ",
    var content: String = " ",
    var createdTime: Timestamp = Timestamp.now(),
    var title: String = " ",
    var uid: String = " ",
    val email: String = "",
    val id: String = "justinyang29",
    val name: String = "Justin",
    val image: String = ""

)
