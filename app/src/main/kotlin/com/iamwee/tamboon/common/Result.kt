package com.iamwee.tamboon.common

infix fun <L, R> Result<L, R>.success(f: (L) -> Unit) = fold(f) {}

infix fun <L, R> Result<L, R>.error(f: (R) -> Unit) = fold({}, f)

fun <L, R> Result<L, R>.fold(completion: (L) -> Unit, failure: (R) -> Unit) = when (this) {
    is Result.Success -> completion(this.data)
    is Result.Error -> failure(this.error)
}

sealed class Result<out L, out R> {
    class Success<L>(val data: L) : Result<L, Nothing>()
    class Error<R>(val error: R) : Result<Nothing, R>()
}