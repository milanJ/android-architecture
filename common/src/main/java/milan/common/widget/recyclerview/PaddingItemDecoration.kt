package milan.common.widget.recyclerview

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * EdgeDecorator
 *
 * @param padding padding set on the left side of the first item and the right side of the last item
 */
class PaddingItemDecoration(private val padding: Int, private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemPosition = parent.getChildAdapterPosition(view)

        // No position, leave it be.
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        if (itemPosition == 0) {
            // First item and last item.
            outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)
        } else {
            // Every other item.
            outRect.set(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

            if (orientation == VERTICAL) {
                outRect.set(view.paddingLeft, view.paddingTop + padding, view.paddingRight, view.paddingBottom)
            } else {
                outRect.set(view.paddingLeft + padding, view.paddingTop, view.paddingRight, view.paddingBottom)
            }
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }
}
