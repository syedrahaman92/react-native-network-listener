package com.networklistener

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.facebook.react.bridge.Arguments.createMap
import com.facebook.react.bridge.ReactApplicationContext

class NetworkListenerModule(val reactContext: ReactApplicationContext) :
  NativeNetworkListenerSpec(reactContext) {


  override fun getName() = NAME

  override fun startNetworkListening() {
    val cm = reactContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    if (cm != null) {
      networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
          sendNetworkStatus(true, getConnectionType(cm))
          Log.d("NetworkStatus", "Network available")
        }

        override fun onLost(network: Network) {
          sendNetworkStatus(false, "none")
          Log.d("NetworkStatus", "Network lost")
        }
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        networkCallback?.let { cm.registerDefaultNetworkCallback(it) }
      } else {
        val request = NetworkRequest.Builder().build()
        networkCallback?.let { cm.registerNetworkCallback(request, it) }
      }
    }
  }

  override fun stopNetworkListening() {
    val cm = reactContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (cm != null) {
      networkCallback?.let { cm.unregisterNetworkCallback(it) }
      networkCallback = null
    }
  }

  private fun sendNetworkStatus(isConnected: Boolean, type: String?) {
    val map = createMap()
    map.putBoolean("isConnected", isConnected)
    map.putString("type", type)
    emitNetworkStatusChanged(map)
  }


  private fun getConnectionType(cm: ConnectivityManager): String {
    val network = cm.getActiveNetwork()
    if (network != null) {
      val caps = cm.getNetworkCapabilities(network)
      if (caps != null) {
        if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
          return "wifi"
        } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
          return "cellular"
        }
      }
    }
    return "unknown"
  }

  companion object {
    const val NAME = NativeNetworkListenerSpec.NAME
    var networkCallback: NetworkCallback? = null
  }
}
