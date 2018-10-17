package com.iamwee.tamboon.ui.charity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.iamwee.tamboon.base.BaseViewModel
import com.iamwee.tamboon.common.error
import com.iamwee.tamboon.common.success
import com.iamwee.tamboon.data.Charity
import com.iamwee.tamboon.data.repository.DefaultCharityRepository
import com.iamwee.tamboon.domain.GetCharitiesUseCase
import com.iamwee.tamboon.http.HttpManager
import com.iamwee.tamboon.utils.NetworkHandler

class CharityViewModel(
        private val getCharities: GetCharitiesUseCase
) : BaseViewModel() {

    val charities: MutableLiveData<List<Charity>> = MutableLiveData()


    fun didLoadCharities() {
        getCharities(Unit) {
            it success { charities.value = it }
            it error { failure.value = it }
        }
    }

}

class CharityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = DefaultCharityRepository(NetworkHandler(context), HttpManager.tamboonService)
        val useCase = GetCharitiesUseCase(repository)
        return CharityViewModel(useCase) as T
    }

}
