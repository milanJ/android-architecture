package milan.common.widget.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class OnScrollRecyclerViewListener(private val layoutManager: LinearLayoutManager,
                                            private val adapter: RecyclerView.Adapter<*>) : RecyclerView.OnScrollListener() {

    private val oldItemPositions = IntArray(2)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val firstVip = layoutManager.findFirstVisibleItemPosition()
        val lastVip = layoutManager.findLastVisibleItemPosition()

        if (oldItemPositions[0] != firstVip || oldItemPositions[1] != lastVip) {
            val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            val lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

            onScrolled(firstVip, lastVip, firstCompletelyVisibleItemPosition,
                    lastCompletelyVisibleItemPosition, adapter.itemCount, recyclerView.scrollState)

            oldItemPositions[0] = firstVip
            oldItemPositions[1] = lastVip
        }
    }

    abstract fun onScrolled(firstVisibleItemPosition: Int,
                            lastVisibleItemPosition: Int,
                            firstCompletelyVisibleItemPosition: Int,
                            lastCompletelyVisibleItemPosition: Int,
                            totalItemCount: Int,
                            scrollState: Int)
}
