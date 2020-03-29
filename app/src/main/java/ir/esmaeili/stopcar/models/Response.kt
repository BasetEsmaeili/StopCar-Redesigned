package ir.esmaeili.stopcar.models

sealed class Response<out T> {
    class Loading<out T> : Response<T>()
    class Empty<out T> : Response<T>()
    class None<out T> : Response<T>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error<out T>(val throwable: Throwable) : Response<T>()
}