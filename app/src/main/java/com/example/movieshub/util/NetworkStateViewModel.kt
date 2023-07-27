package com.example.movieshub.util

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class NetworkStateViewModel(private val application: Application) : AndroidViewModel(application) {

    private val networkStateLiveData = MutableLiveData<Boolean>()
    val networkState: MutableLiveData<Boolean>
        get() = networkStateLiveData

    fun getNetworkUpdate() {
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR).build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkStateLiveData.postValue(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkStateLiveData.postValue(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkStateLiveData.postValue(false)

            }
        }

        val connectivityManager =
            application.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

}