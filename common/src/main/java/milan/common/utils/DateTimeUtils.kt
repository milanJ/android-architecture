package milan.common.utils

import java.util.*

fun Calendar.isSameDay(other: Calendar): Boolean {
    return get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR) && get(Calendar.YEAR) == other.get(Calendar.YEAR)
}

fun Calendar.isBefore(other: Calendar): Boolean {
    return timeInMillis < other.timeInMillis
}

fun Calendar.isAfter(other: Calendar): Boolean {
    return timeInMillis > other.timeInMillis
}

fun Calendar.isInFuture(): Boolean {
    return timeInMillis > Calendar.getInstance().timeInMillis
}
