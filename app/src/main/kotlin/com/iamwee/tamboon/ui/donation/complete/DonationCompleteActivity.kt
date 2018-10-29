package com.iamwee.tamboon.ui.donation.complete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseActivity
import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.ui.MainActivity
import com.iamwee.tamboon.utils.CurrencyFormatter
import kotlinx.android.synthetic.main.activity_donation_complete.*

class DonationCompleteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_complete)

        val amount = intent.getLongExtra(EXTRA_AMOUNT, -1)
        val charity = intent.getParcelableExtra<Charity>(EXTRA_CHARITY)

        if (amount < 0 || charity == null) {
            textViewDonationSuccessMessage.visibility = View.GONE
        } else {
            textViewDonationSuccessMessage.text = getString(
                R.string.message_donate_successfully,
                CurrencyFormatter.format(amount),
                charity.name
            )
        }

        buttonDonationSuccessClose.setOnClickListener { launchHomeActivity() }

    }

    private fun launchHomeActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        //Do nothing
    }

    companion object {
        private const val EXTRA_AMOUNT = "extra.donationcompleteactivity.amount"
        private const val EXTRA_CHARITY = "extra.donationcompleteactivity.charity"

        fun launch(context: Context, amount: Long, charity: Charity) {
            val intent = Intent(context, DonationCompleteActivity::class.java).apply {
                putExtra(EXTRA_AMOUNT, amount)
                putExtra(EXTRA_CHARITY, charity)
            }
            context.startActivity(intent)
        }
    }
}