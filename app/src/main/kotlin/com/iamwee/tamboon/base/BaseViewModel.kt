package com.iamwee.tamboon.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val failure: MutableLiveData<Exception> = MutableLiveData()

}