package milan.common.widget.recyclerview

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * EdgeDecorator
 *
 * @param edgePadding padding set on the left side of the first item and the right side of the last item
 */
class EdgePaddingItemDecoration(private val edgePadding: Int, private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        // No position, leave it be.
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        if (itemPosition == 0) {
            // First item.
            if (orientation == VERTICAL) {
                outRect.set(view.paddingLeft, view.paddingTop + edgePadding, view.paddingRight, view.paddingBottom)
            } else {
                outRect.set(view.paddingLeft + edgePadding, view.paddingTop, view.paddingRight, view.paddingBottom)
            }
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            // Last item.
            if (orientation == VERTICAL) {
                outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom + edgePadding)
            } else {
                outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight + edgePadding, view.paddingBottom)
            }
        } else {
            // Every other item.
            outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }
}
