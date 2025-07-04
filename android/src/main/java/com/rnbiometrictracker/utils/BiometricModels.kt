package com.rnbiometrictracker.utils

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

interface BioResultCode {
  val code: Int
  val message: String
}

enum class BiometricResult(override val code: Int, override val message: String) : BioResultCode {
  BIOMETRIC_NOT_SUPPORTED(2001, "This device doesn't have biometric features"),
  BIOMETRIC_NONE_ENROLLED(2002, "No biometrics have been enrolled on the device"),
  BIOMETRIC_REGISTRATION_SUCCESS(2003, "Biometric registered successfully"),
  BIOMETRIC_REGISTRATION_FAILED(2004, "Biometrics registration failed"),
  BIOMETRIC_DEREGISTER_SUCCESS(2005, "Biometrics deregistered successfully"),
  BIOMETRIC_DEREGISTER_FAILED(2006, "Biometrics deregister failed"),
  BIOMETRIC_NOT_ENABLED(2007, "Biometric not enabled"),
  BIOMETRIC_CHANGE_DETECTED(2008, "Biometric data has changed"),
  BIOMETRIC_NOT_CHANGED(2009, "No changes detected in biometric data")
}

fun BiometricResult.toResponse() = BiometricResponse(this.code, this.message)

data class BiometricResponse(val resultCode: Int, val message: String)

fun BiometricResponse.toWritableMap(): WritableMap {
  val map = Arguments.createMap()
  map.putInt("resultCode", this.resultCode)
  map.putString("message", this.message)
  return map
}
