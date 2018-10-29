package com.iamwee.tamboon.ui.donation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.iamwee.tamboon.base.BaseViewModel

class DonationViewModel: BaseViewModel() {

    val donationValidated: MutableLiveData<Boolean> = MutableLiveData()

}

class DonationViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DonationViewModel() as T
    }
}