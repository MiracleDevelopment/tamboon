package com.iamwee.tamboon.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}