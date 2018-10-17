package com.iamwee.tamboon.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Crypto {

    fun encrypt(key: String, value: String): String {
        val secretKey = createSecretKey(key)
        val cipher = createCipher()
        val iv = createIvParameterSpec()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        val encryptedValue = cipher.doFinal(value.toByteArray())
        val ivAndEncryptedValue = iv.iv + encryptedValue
        return Base64.encodeToString(ivAndEncryptedValue, Base64.NO_WRAP)
    }

    fun decrypt(key: String, encryptedValue: String): String {
        if (encryptedValue.isBlank()) return ""

        val secretKey = createSecretKey(key)
        val cipher = createCipher()
        val iv = createIvParameterSpec()
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

        val decodedEncryptedValue = Base64.decode(encryptedValue, Base64.NO_WRAP)
        val decryptedValue = cipher.doFinal(decodedEncryptedValue)
        val decryptedValueWithoutIv = decryptedValue.sliceArray(16 until decryptedValue.size)
        return String(decryptedValueWithoutIv)
    }

    private fun createSecretKey(password: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(password.toByteArray())
        val key = digest.digest()
        return SecretKeySpec(key, "AES")
    }

    private fun createCipher(): Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")

    private fun createIvParameterSpec(): IvParameterSpec {
        val randomGenerated16ByteArray = SecureRandom().generateSeed(16)
        return IvParameterSpec(randomGenerated16ByteArray)
    }
}