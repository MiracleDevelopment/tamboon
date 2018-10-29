package com.iamwee.tamboon.common

import android.arch.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}