package milan.common.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerticalGridPaddingItemDecoration(private val gridLayoutManager: GridLayoutManager,
                                        private val padding: Int,
                                        private val edgePadding: Int) : RecyclerView.ItemDecoration() {

    private val numberOfColumns: Int = gridLayoutManager.spanCount
    private val orientation: Int = gridLayoutManager.orientation
    private val halfPadding: Int = padding / 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        // No position, leave it be.
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val paddingTop: Int
        if (applyTop(itemPosition)) {
            paddingTop = view.paddingTop + halfPadding
        } else {
            paddingTop = view.paddingTop + edgePadding
        }

        val paddingBottom: Int
        if (applyBottom(itemPosition, itemCount)) {
            paddingBottom = view.paddingBottom + halfPadding
        } else {
            paddingBottom = view.paddingBottom + edgePadding
        }

        val paddingStart: Int
//        if (applyStart(itemPosition)) {
//            paddingStart = view.paddingStart + halfPadding
//        } else {
//            paddingStart = view.paddingStart
//        }
        paddingStart = view.paddingStart

        val paddingEnd: Int
//        if (applyEnd(itemPosition)) {
//            paddingEnd = view.paddingEnd + halfPadding
//        } else {
//            paddingEnd = view.paddingEnd
//        }
        paddingEnd = view.paddingEnd

        outRect.set(paddingStart, paddingTop, paddingEnd, paddingBottom)
    }

    fun applyTop(itemPosition: Int): Boolean {
        if (itemPosition / numberOfColumns != 0) {
            return true
        }
        return false
    }

    fun applyBottom(itemPosition: Int, itemCount: Int): Boolean {
        if (itemPosition / numberOfColumns == calcRowCount(itemCount) - 1) {
            return false
        }
        return true
    }

    fun applyStart(itemPosition: Int): Boolean {
        if (itemPosition % numberOfColumns == 0) {
            return false
        }
        return true
    }

    fun applyEnd(itemPosition: Int): Boolean {
        if (itemPosition % numberOfColumns == numberOfColumns - 1) {
            return false
        }
        return true
    }

    fun calcRowCount(itemCount: Int): Int {
        return (itemCount + (numberOfColumns - 1)) / numberOfColumns
    }
}
