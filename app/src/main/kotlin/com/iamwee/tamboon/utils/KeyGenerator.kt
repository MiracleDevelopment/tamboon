package com.iamwee.tamboon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import com.iamwee.tamboon.BuildConfig
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object KeyGenerator {

    @Suppress("ObjectPropertyName")
    private var _hashKey: String = ""

    val key: String
        get() = _hashKey

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures", "WrongConstant")
    fun init(context: Context) {
        try {
            val signatures = if (Build.VERSION.SDK_INT >= 28) {
                (context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ) as PackageInfo).signingInfo.apkContentsSigners
            } else {
                (context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
                ) as PackageInfo).signatures
            }

            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                _hashKey = String(Base64.encode(md.digest(), 0)).trim { it <= ' ' }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            _hashKey = ""
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            _hashKey = ""
        }

        if (BuildConfig.DEBUG) {
            Log.d("KeyGenerator", _hashKey)
        }

    }

}