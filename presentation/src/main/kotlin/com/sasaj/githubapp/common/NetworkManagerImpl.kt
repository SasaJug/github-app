package com.sasaj.githubapp.common

import android.net.ConnectivityManager
import com.sasaj.domain.NetworkManager

class NetworkManagerImpl(private val connectivityManager: ConnectivityManager) : NetworkManager {
    override fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}