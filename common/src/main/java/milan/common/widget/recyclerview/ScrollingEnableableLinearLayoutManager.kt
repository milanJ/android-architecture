package milan.common.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class ScrollingEnableableLinearLayoutManager : LinearLayoutManager {

    private var isHorizontalScrollingEnabled: Boolean
    private var isVerticalScrollingEnabled: Boolean

    init {
        isHorizontalScrollingEnabled = true
        isVerticalScrollingEnabled = true
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, @RecyclerView.Orientation orientation: Int,
                reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun canScrollHorizontally(): Boolean {
        return isHorizontalScrollingEnabled && super.canScrollHorizontally()
    }

    override fun canScrollVertically(): Boolean {
        return isVerticalScrollingEnabled && super.canScrollVertically()
    }

    fun setHorizontalScrollingEnabled(enabled: Boolean): ScrollingEnableableLinearLayoutManager {
        isHorizontalScrollingEnabled = enabled
        return this
    }

    fun setVerticalScrollingEnabled(enabled: Boolean): ScrollingEnableableLinearLayoutManager {
        isVerticalScrollingEnabled = enabled
        return this
    }
}
