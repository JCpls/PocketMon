package com.justin.pocketmon.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.justin.pocketmon.PocketmonApplication

object Util {

    /**
     * Determine and monitor the connectivity status
     *
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     */
    fun isInternetConnected(): Boolean {
        val cm = PocketmonApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return PocketmonApplication.instance.getString(resourceId)
    }

//    fun getColor(resourceId: Int): Int {
//        return PocketmonApplication.instance.getColor(resourceId)
//    }
}
