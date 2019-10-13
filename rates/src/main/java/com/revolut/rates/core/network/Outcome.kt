package com.revolut.rates.core.network

sealed class Outcome<T> {
    data class Progress<T>(var loading: Boolean) : Outcome<T>()
    data class Success<T>(var data: T) : Outcome<T>()
    data class Failed<T>(val e: Throwable) : Outcome<T>()

    companion object {
        fun <T> progress(isLoading: Boolean): Outcome<T> =
            Progress(isLoading)

        fun <T> success(data: T): Outcome<T> =
            Success(data)

        fun <T> failed(e: Throwable): Outcome<T> =
            Failed(e)
    }
}