package com.rnbiometrictracker

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise

abstract class RnBiometricTrackerSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun enableBiometric(promise: Promise)

  abstract fun disableBiometric(promise: Promise)

  abstract fun isBiometricEnabled(promise: Promise)

  abstract fun isBiometricChanged(promise: Promise)
}
