package milan.common.utils

import java.io.Serializable;

/**
 * Represents a quartet of values
 *
 * There is no meaning attached to values in this class, it can be used for any purpose.
 * Quartet exhibits value semantics, i.e. two quartets are equal if all four components are equal.
 *
 * @param A type of the first value.
 * @param B type of the second value.
 * @param C type of the third value.
 * @param D type of the fourth value.
 * @property first First value.
 * @property second Second value.
 * @property third Third value.
 * @property fourth Fourth value.
 */
data class Quartet<out A, out B, out C, out D>(
        public val first: A,
        public val second: B,
        public val third: C,
        public val fourth: D
) : Serializable {

    /**
     * Returns string representation of the [Quartet] including its [first], [second], [third] and [fourth] values.
     */
    public override fun toString(): String = "($first, $second, $third, $fourth)"
}
