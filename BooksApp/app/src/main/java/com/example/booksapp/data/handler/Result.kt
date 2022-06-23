package com.example.booksapp.data.handler

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data] State"
            is Error -> "Error[exception=$error] State"
            is Loading -> "Is loading data"
        }
    }
}