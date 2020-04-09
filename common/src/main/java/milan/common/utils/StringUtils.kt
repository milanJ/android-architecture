package milan.common.utils

import android.util.Patterns

fun String.isEmailValid(): Boolean {
    return if (contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    } else {
        isNotBlank()
    }
}
