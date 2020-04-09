package milan.common.widget.scrollview

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView


class NoFocusScrollingScrollView @JvmOverloads constructor(context: Context,
                                                           attrs: AttributeSet? = null,
                                                           defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    override fun computeScrollDeltaToGetChildRectOnScreen(rect: android.graphics.Rect): Int {
        return 0
    }
}
