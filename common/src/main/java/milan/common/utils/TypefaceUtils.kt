package milan.common.utils

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView

fun setTypeface(container: ViewGroup?, typeface: Typeface) {
    if (container == null) {
        return
    }

    val childCount = container.childCount

    for (i in 0 until childCount) {
        val childView = container.getChildAt(i)
        if (childView is TextView) {
            childView.typeface = typeface
        } else if (childView is ViewGroup) {
            setTypeface(childView, typeface)
        }
    }
}
