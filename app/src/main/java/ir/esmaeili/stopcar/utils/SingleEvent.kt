package ir.esmaeili.stopcar.utils

class SingleEvent<out T>(private val content: T) {
    var hasBeenHandled = false
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled)
            null
        else {
            hasBeenHandled = true
            content
        }
    }
}