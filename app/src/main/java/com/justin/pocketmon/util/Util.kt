package com.justin.pocketmon.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.justin.pocketmon.PocketmonApplication

object Util {

    fun getString(resourceId: Int): String {
        return PocketmonApplication.instance.getString(resourceId)
    }
}
