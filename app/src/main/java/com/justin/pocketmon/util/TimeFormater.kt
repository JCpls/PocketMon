package com.justin.pocketmon.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDateAndWeek(time: Long): String {
    return if (android.os.Build.VERSION.SDK_INT >= 24) {
        SimpleDateFormat("YYYY/MM/dd EE").format(time)
    } else {
        val tms = Calendar.getInstance()
        tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                tms.get(Calendar.MONTH).toString() + "/" +
                tms.get(Calendar.YEAR).toString() + " " +
                tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                tms.get(Calendar.MINUTE).toString() + ":" +
                tms.get(Calendar.SECOND).toString()
    }
}