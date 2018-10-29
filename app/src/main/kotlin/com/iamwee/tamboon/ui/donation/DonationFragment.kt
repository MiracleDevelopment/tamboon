package com.iamwee.tamboon.ui.donation

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import co.omise.android.models.Token
import co.omise.android.ui.CreditCardActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseFragment
import com.iamwee.tamboon.common.*
import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.ui.donation.complete.DonationCompleteActivity
import com.iamwee.tamboon.utils.*
import kotlinx.android.synthetic.main.fragment_donation.*

@Suppress("UNUSED_PARAMETER")
class DonationFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_donation

    private val viewModel by lazy {
        viewModelProvider<DonationViewModel>(DonationViewModelFactory(requireContext())) {
            observe(donationValidated, ::handleDonationButtonEnabled)
            observe(failure, ::handleFailure)
            observeEvent(showDialogEvent, ::showLoadingDialog)
            observeEvent(dismissDialogEvent, ::dismissLoadingDialog)
            observeEvent(donationSuccessEvent, ::launchDonationFinishActivity)
        }
    }

    private val charity by lazy {
        arguments?.getParcelable<Charity>(EXTRA_DONATION_FRAGMENT_CHARITY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (charity == null) return
        charity?.let {

            textViewDonationTo.text = getString(R.string.label_donation_to, it.name)

            Glide.with(this@DonationFragment)
                .load(it.logoUrl)
                .apply(RequestOptions().placeholder(R.drawable.thumbnail_background))
                .apply(RequestOptions().error(R.drawable.thumbnail_background))
                .apply(RequestOptions.fitCenterTransform())
                .into(imageViewCharityThumbnail)

            editTextDonationAmount.addTextChangedListener(DonationTextWatcher(::handleButton))
            buttonDonation.setOnClickListener(::launchCreditCardActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CREDIT_CARD_REQUEST -> {
                if (resultCode == RESULT_CANCELED) {
                    return
                }

                data?.getParcelableExtra<Token>(CreditCardActivity.EXTRA_TOKEN_OBJECT)?.let {
                    val donateAmountSatang = editTextDonationAmount.textString.toLong().times(1000)
                    viewModel.donate(it, donateAmountSatang)
                }
                super.onActivityResult(requestCode, resultCode, data)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleDonationButtonEnabled(isValidated: Boolean?) {
        buttonDonation.isEnabled = isValidated == true
    }

    private fun handleFailure(e: Exception?) {
        val errorMessage = e?.errorMessage ?: return
        AlertDialog.Builder(requireContext())
            .setMessage(errorMessage)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun launchDonationFinishActivity(unit: Unit) {
        DonationCompleteActivity.launch(requireContext(), editTextDonationAmount.textString.toLong(), charity!!)
    }

    private fun showLoadingDialog(unit: Unit) {
        ProgressDialog.show(childFragmentManager)
    }

    private fun dismissLoadingDialog(unit: Unit) {
        ProgressDialog.dismiss(childFragmentManager)
    }

    private fun handleButton(amount: Long) {
        viewModel.donationValidated.postValue(amount > 0)
        buttonDonation.text = if (amount > 0) {
            getString(R.string.button_donation, CurrencyFormatter.format(amount))
        } else {
            getString(R.string.button_please_fill_donation_amount)
        }
    }

    private fun launchCreditCardActivity(view: View) {
        val intent = Intent(requireContext(), CreditCardActivity::class.java).apply {
            putExtra(CreditCardActivity.EXTRA_PKEY, Crypto.decrypt(KeyGenerator.key, Config.pkey))
        }
        startActivityForResult(intent, CREDIT_CARD_REQUEST)
    }

    companion object {

        private const val EXTRA_DONATION_FRAGMENT_CHARITY = "extra.donationfragment.charity"
        private const val CREDIT_CARD_REQUEST = 4241

        fun withCharity(charity: Charity) = DonationFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_DONATION_FRAGMENT_CHARITY, charity)
            }
        }

    }
}