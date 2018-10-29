package com.iamwee.tamboon.ui.donation

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseFragment
import com.iamwee.tamboon.common.observe
import com.iamwee.tamboon.common.viewModelProvider
import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.utils.CurrencyFormatter
import com.iamwee.tamboon.utils.DonationTextWatcher
import kotlinx.android.synthetic.main.fragment_donation.*

class DonationFragment: BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_donation

    private val viewModel by lazy {
        viewModelProvider<DonationViewModel>(DonationViewModelFactory()) {
            observe(donationValidated, ::handleDonationButtonEnabled)
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
        }

    }

    private fun handleDonationButtonEnabled(isValidated: Boolean?) {
        buttonDonation.isEnabled = isValidated == true
    }

    private fun handleButton(amount: Long) {
        viewModel.donationValidated.postValue(amount > 0)
        buttonDonation.text = if (amount > 0) {
            getString(R.string.button_donation, CurrencyFormatter.format(amount))
        } else {
            getString(R.string.button_please_fill_donation_amount)
        }
    }

    companion object {

        private const val EXTRA_DONATION_FRAGMENT_CHARITY = "extra.donationfragment.charity"

        fun withCharity(charity: Charity) = DonationFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_DONATION_FRAGMENT_CHARITY, charity)
            }
        }

    }
}