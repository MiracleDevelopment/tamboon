package com.iamwee.tamboon.utils

import android.content.Context
import com.iamwee.tamboon.common.networkInfo

class NetworkHandler(private val context: Context) {

    val isConnected: Boolean?
        get() = context.networkInfo?.isConnected

}