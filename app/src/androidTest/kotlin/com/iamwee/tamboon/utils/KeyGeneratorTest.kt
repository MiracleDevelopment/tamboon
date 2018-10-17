package com.iamwee.tamboon.utils

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeyGeneratorTest {

    lateinit var key: String
    private val TAG = "KeyGeneratorTest"

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        KeyGenerator.init(context)
        key = KeyGenerator.key
    }

    @Test
    fun verifyThatKeyShouldNotEmptyString() {
        print(key)
        assertEquals(true, key.isNotEmpty())
    }

    @Test
    fun verifyThatShouldEncryptDataAndShouldDecryptBackToRawData() {

        val rawData = "This is a raw data to test encryption and decryption"

        val encrypt = Crypto.encrypt(key, rawData)

        Log.d(TAG, encrypt)

        val decrypt = Crypto.decrypt(key, encrypt)

        Log.d(TAG, decrypt)

        assertEquals(rawData, decrypt)

    }

}