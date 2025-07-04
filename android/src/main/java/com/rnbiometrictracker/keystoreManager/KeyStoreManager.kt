package com.rnbiometrictracker.keystoreManager

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import com.rnbiometrictracker.utils.BiometricResponse
import com.rnbiometrictracker.utils.BiometricResult
import com.rnbiometrictracker.utils.toResponse
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyStoreManager private constructor(
  private val context: Context
) {

  private val keyName: String = "${context.packageName}.biometric"


  @RequiresApi(Build.VERSION_CODES.N)
  fun generateSecretKey(): BiometricResponse {
    return try {

      val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

      if (keyStore.containsAlias(keyName)) {
        return BiometricResult.BIOMETRIC_REGISTRATION_SUCCESS.toResponse()
      }

      val keyGenerator =
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

      keyGenerator.init(
        KeyGenParameterSpec.Builder(
          keyName, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
          .setUserAuthenticationRequired(true).setInvalidatedByBiometricEnrollment(true).build()
      )
      keyGenerator.generateKey()

      BiometricResult.BIOMETRIC_REGISTRATION_SUCCESS.toResponse()
    } catch (e: Exception) {
      BiometricResult.BIOMETRIC_REGISTRATION_FAILED.toResponse()
    }
  }


  fun getSecretKey(): SecretKey? {
    return try {
      val keyStore = KeyStore.getInstance("AndroidKeyStore")
      keyStore.load(null)
      keyStore.getKey(keyName, null) as SecretKey?
    } catch (e: Exception) {
      null
    }
  }

  fun deleteSecretKey(): BiometricResponse {
    return try {
      val keyStore = KeyStore.getInstance("AndroidKeyStore")
      keyStore.load(null)
      keyStore.deleteEntry(keyName)
      return BiometricResult.BIOMETRIC_DEREGISTER_SUCCESS.toResponse()
    } catch (e: Exception) {
      return BiometricResult.BIOMETRIC_DEREGISTER_FAILED.toResponse()
    }
  }

  fun getCipher(): Cipher {
    return Cipher.getInstance(
      "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
    )
  }

  companion object {
    @Volatile
    private var INSTANCE: KeyStoreManager? = null

    fun getInstance(context: Context): KeyStoreManager {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: KeyStoreManager(context).also { INSTANCE = it }
      }
    }
  }
}
