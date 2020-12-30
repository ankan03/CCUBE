@file:Suppress("DEPRECATION")

package com.studgenie.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager


object InternetConnectivity {

    const val NETWORK_TYPE_EHRPD = 14 // Level 11
    const val NETWORK_TYPE_EVDO_B = 12 // Level 9
    const val NETWORK_TYPE_HSPAP = 15 // Level 13
    const val NETWORK_TYPE_IDEN = 11 // Level 8
    const val NETWORK_TYPE_LTE = 13 // Level 11

//    Check if there is any connectivity
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

//    Check if there is fast connectivity
    fun isConnectedFast(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected && isConnectionFast(info.type, info.subtype)
    }

//    Check if the connection is fast
    fun isConnectionFast(type: Int, subType: Int): Boolean {
        return if (type == ConnectivityManager.TYPE_WIFI) {
            println("CONNECTED VIA WIFI")
            true
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
                NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps
                NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps
                NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps
                NETWORK_TYPE_IDEN -> false // ~25 kbps
                NETWORK_TYPE_LTE -> true // ~ 10+ Mbps
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                else -> false
            }
        } else {
            false
        }
    }
}