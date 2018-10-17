package com.iamwee.tamboon.ui

import android.os.Bundle
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseActivity
import com.iamwee.tamboon.ui.charity.CharityListFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, CharityListFragment())
                    .commit()
        }
    }
}
