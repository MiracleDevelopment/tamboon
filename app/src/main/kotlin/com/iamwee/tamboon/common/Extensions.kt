package com.iamwee.tamboon.common

import android.arch.lifecycle.*
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import com.iamwee.tamboon.BuildConfig


infix fun ViewGroup.inflate(layoutId: Int) = LayoutInflater.from(context).inflate(layoutId, this, false)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        factory: ViewModelProvider.Factory, body: VM.() -> Unit = {}
): VM {
    val viewModel = ViewModelProviders.of(this, factory).get(VM::class.java)
    viewModel.body()
    return viewModel
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <T: Any, L: LiveData<Event<T>>> LifecycleOwner.observeEvent(liveData: L, body: (T) -> Unit) =
    liveData.observe(this, EventObserver(body))

val EditText.textString: String
    get() = this.text.toString()

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

val Exception.errorMessage: String
    get() = when (this) {
        is NetworkConnectionException -> this.message
        is ServerException -> this.message
        is ClientErrorException -> this.message
        else -> {
            if (BuildConfig.DEBUG) {
                this.localizedMessage
            } else {
                "There are something when wrong, please try again later"
            }
        }
    }
