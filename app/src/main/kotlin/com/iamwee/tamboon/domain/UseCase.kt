package com.iamwee.tamboon.domain

import com.iamwee.tamboon.common.Result
import com.iamwee.tamboon.common.Result.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

abstract class UseCase<in Params, out Type : Any> {

    abstract suspend fun execute(params: Params): Type

    operator fun invoke(params: Params, onResult: (Result<Type, Exception>) -> Unit = {}) {
        val job = GlobalScope.async(Dispatchers.IO) { execute(params) }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                onResult(Success(job.await()))
            } catch (e: Exception) {
                onResult(Error(e))
            }
        }
    }
}