package com.networklistener

import com.facebook.react.bridge.ReactApplicationContext

class NetworkListenerModule(reactContext: ReactApplicationContext) :
  NativeNetworkListenerSpec(reactContext) {

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = NativeNetworkListenerSpec.NAME
  }
}
