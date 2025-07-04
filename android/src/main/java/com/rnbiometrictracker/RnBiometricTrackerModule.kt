package com.rnbiometrictracker

import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.rnbiometrictracker.keystoreManager.KeyStoreManager
import com.rnbiometrictracker.utils.BiometricResult
import com.rnbiometrictracker.utils.toResponse
import com.rnbiometrictracker.utils.toWritableMap
import java.security.InvalidKeyException
import javax.crypto.Cipher

class RnBiometricTrackerModule internal constructor(
  private val reactContext: ReactApplicationContext
) : RnBiometricTrackerSpec(reactContext) {

  private val keyStoreManager: KeyStoreManager = KeyStoreManager.getInstance(reactContext)

  override fun getName(): String {
    return NAME
  }

  private fun isBiometricEnrolled(): Boolean {
    val biometricManager = BiometricManager.from(reactContext)
    return biometricManager.canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
  }

  private fun isBiometricEnabled(): Boolean {
    return keyStoreManager.getSecretKey() != null
  }

  @ReactMethod
  override fun enableBiometric(promise: Promise) {
    try {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        promise.resolve(BiometricResult.BIOMETRIC_NOT_SUPPORTED.toResponse().toWritableMap());
        return;
      }
      if (isBiometricEnrolled().not()) {
        promise.resolve(BiometricResult.BIOMETRIC_NONE_ENROLLED.toResponse().toWritableMap())
        return;
      }
      val result = keyStoreManager.generateSecretKey();
      promise.resolve(result.toWritableMap());
    } catch (e: Exception) {
      promise.reject("BIOMETRIC_ERROR", e.message, e)
    }
  }

  @ReactMethod
  override fun disableBiometric(promise: Promise) {
    try {
      if (isBiometricEnabled().not()) {
        promise.resolve(BiometricResult.BIOMETRIC_NOT_ENABLED.toResponse().toWritableMap())
        return
      }
      val result = keyStoreManager.deleteSecretKey();
      promise.resolve(result.toWritableMap());
    } catch (e: Exception) {
      promise.reject("BIOMETRIC_ERROR", e.message, e)
    }
  }

  @ReactMethod
  override fun isBiometricEnabled(promise: Promise) {
    try {
      promise.resolve(isBiometricEnabled())
    } catch (e: Exception) {
      promise.reject("BIOMETRIC_ERROR", e.message, e)
    }
  }

  @ReactMethod
  override fun isBiometricChanged(promise: Promise) {
    try {
      if (isBiometricEnabled().not()) {
        promise.resolve(BiometricResult.BIOMETRIC_NOT_ENABLED.toResponse().toWritableMap())
        return
      }

      val secretKey = keyStoreManager.getSecretKey()
      val cipher = keyStoreManager.getCipher()
      if (secretKey == null) {
        promise.resolve(BiometricResult.BIOMETRIC_CHANGE_DETECTED.toResponse().toWritableMap())
        return
      }
      cipher.init(Cipher.ENCRYPT_MODE, secretKey)
      promise.resolve(BiometricResult.BIOMETRIC_NOT_CHANGED.toResponse().toWritableMap())
    } catch (e: InvalidKeyException) {
      promise.resolve(BiometricResult.BIOMETRIC_CHANGE_DETECTED.toResponse().toWritableMap())
    } catch (e: Exception) {
      promise.resolve(BiometricResult.BIOMETRIC_CHANGE_DETECTED.toResponse().toWritableMap())
    }
  }

  companion object {
    const val NAME = "RnBiometricTracker"
  }
}
