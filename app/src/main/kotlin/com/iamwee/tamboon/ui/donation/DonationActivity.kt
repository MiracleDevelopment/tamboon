package com.iamwee.tamboon.ui.donation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseActivity
import com.iamwee.tamboon.data.Charity

class DonationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.partial_fragment_container)

        val charity = intent.getParcelableExtra<Charity>(EXTRA_DONATION_ACTIVITY_CHARITY) ?: null
        if (charity == null) {
            Toast.makeText(this, "Charity not found.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        supportActionBar?.let {
            it.title = charity.name
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, DonationFragment.withCharity(charity))
                .commit()
        }


    }


    companion object {
        private const val EXTRA_DONATION_ACTIVITY_CHARITY = "DonationActivity.charity"
        fun launch(context: Context, charity: Charity) {
            val intent = Intent(context, DonationActivity::class.java).apply {
                putExtra(EXTRA_DONATION_ACTIVITY_CHARITY, charity)
            }
            context.startActivity(intent)
        }
    }

}