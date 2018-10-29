package com.iamwee.tamboon.ui.donation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import co.omise.android.models.Token
import com.iamwee.tamboon.base.BaseViewModel
import com.iamwee.tamboon.common.Event
import com.iamwee.tamboon.common.error
import com.iamwee.tamboon.common.success
import com.iamwee.tamboon.data.DonateRequest
import com.iamwee.tamboon.data.repository.DefaultDonationRepository
import com.iamwee.tamboon.domain.DonateUseCase
import com.iamwee.tamboon.http.HttpManager
import com.iamwee.tamboon.utils.NetworkHandler

class DonationViewModel(
    private val donateUseCase: DonateUseCase
) : BaseViewModel() {

    val donationValidated: MutableLiveData<Boolean> = MutableLiveData()
    val showDialogEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val dismissDialogEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val donationSuccessEvent: MutableLiveData<Event<Unit>> = MutableLiveData()

    fun donate(token: Token, amount: Long) {
        val request = DonateRequest(
            name = token.card.name,
            token = token.id,
            amount = amount
        )

        showDialogEvent.value = Event(Unit)
        donateUseCase(request) { result ->
            dismissDialogEvent.value = Event(Unit)
            result success {
                donationSuccessEvent.value = Event(Unit)
            }
            result error {
                failure.value = it
            }
        }
    }

}

class DonationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = DefaultDonationRepository(NetworkHandler(context), HttpManager.tamboonService)
        val useCase = DonateUseCase(repository)
        return DonationViewModel(useCase) as T
    }
}