package com.iamwee.tamboon

import android.app.Application
import com.iamwee.tamboon.utils.KeyGenerator

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KeyGenerator.init(this)
    }

}