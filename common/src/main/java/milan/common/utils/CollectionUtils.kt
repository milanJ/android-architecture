package milan.common.utils

fun <T> hasSameContent(c1: Collection<T>?, c2: Collection<T>?): Boolean {
    if (c1 != null && c2 != null && c1.size == c2.size && c1.containsAll(c2)) {
        return true;
    } else if (c1 == null && c2 == null) {
        return true;
    }
    return false
}

/**
 * Can perform deep check.
 */
fun <T> hasSameContent(c1: Collection<T>?, c2: Collection<T>?, comparator: Comparator<T>): Boolean {
    if (c1 != null && c2 != null && c1.size == c2.size) {
        val c1Iterator = c1.iterator()
        val c2Iterator = c2.iterator()

        while (c1Iterator.hasNext()) {
            if (comparator.compare(c1Iterator.next(), c2Iterator.next()) != 0) {
                return false
            }
        }

        return true;
    } else if (c1 == null && c2 == null) {
        return true;
    }
    return false
}
