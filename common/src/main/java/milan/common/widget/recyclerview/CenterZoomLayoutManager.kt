package milan.common.widget.recyclerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * As per in the following articles:
 *  - @see https://stackoverflow.com/questions/35309710/zoom-central-image-recycler-view/35311728#35311728
 *  - @see https://stackoverflow.com/questions/41307578/recycler-view-resizing-item-view-while-scrolling-for-carousel-like-effect
 */
class CenterZoomLayoutManager : LinearLayoutManager {

    private var shrinkDistance = DEFAULT_SHRINK_DISTANCE
    private var shrinkAmount = DEFAULT_SHRINK_AMOUNT

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
        scrollVerticallyBy(0, recycler, state)
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val orientation = orientation
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            val midpoint = width / 2f

            val d0 = 0f
            val d1 = shrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - shrinkAmount

            val childCount = childCount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if(child != null){
                    val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2f
                    val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                    val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                    child.scaleX = scale
                    child.scaleY = scale
                }
            }
            return scrolled
        } else {
            return 0
        }
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        val orientation = orientation
        if (orientation == LinearLayoutManager.VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = shrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - shrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if(child != null){
                    val childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2f
                    val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                    val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                    child.scaleX = scale
                    child.scaleY = scale
                }
            }
            return scrolled
        } else {
            return 0
        }
    }

    fun setShrinkAmount(shrinkAmount: Float): CenterZoomLayoutManager {
        this.shrinkAmount = shrinkAmount
        return this
    }

    fun setShrinkDistance(shrinkDistance: Float): CenterZoomLayoutManager {
        this.shrinkDistance = shrinkDistance
        return this
    }

    companion object {
        private val DEFAULT_SHRINK_DISTANCE = 0.75F
        private val DEFAULT_SHRINK_AMOUNT = 0.5F
    }
}
