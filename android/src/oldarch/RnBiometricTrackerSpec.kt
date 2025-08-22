package com.rnbiometrictracker

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.Promise

abstract class RnBiometricTrackerSpec internal constructor(context: ReactApplicationContext) :
  ReactContextBaseJavaModule(context) {

  abstract fun enableBiometricTracker(promise: Promise)

  abstract fun disableBiometricTracker(promise: Promise)

  abstract fun isBiometricTrackerEnabled(promise: Promise)

  abstract fun isBiometricChanged(promise: Promise)
}
