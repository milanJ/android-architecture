package milan.common.widget.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Deprecated(message = "Not working well when item's top view is a CardView")
class EqualHorizontalSpacingVerticalGridItemDecoration(private val gridLayoutManager: GridLayoutManager,
                                                       private val itemWidth: Int) : RecyclerView.ItemDecoration() {

    private val numberOfColumns: Int = gridLayoutManager.spanCount

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)

        // No position, leave it be.
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        val totalWidth = parent.width
        if (totalWidth > 0) {
            val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

            val divider = ((totalWidth - ((itemWidth + view.paddingStart + view.paddingEnd) * numberOfColumns)) / (numberOfColumns + 1))
            val dividerHalf = divider / 2

            val paddingStart: Int
            if (spanIndex == 0) {
                paddingStart = view.paddingStart + divider
            } else {
                paddingStart = view.paddingStart + dividerHalf
            }

            val paddingEnd: Int
            if (spanIndex == numberOfColumns - 1) {
                paddingEnd = view.paddingEnd + divider
            } else {
                paddingEnd = view.paddingEnd + dividerHalf
            }

            outRect.set(paddingStart, view.paddingTop, paddingEnd, view.paddingBottom)

//            outRect.left = paddingStart
//            outRect.right = paddingEnd
        }
    }
}
