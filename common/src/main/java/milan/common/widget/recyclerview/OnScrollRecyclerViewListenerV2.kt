package milan.common.widget.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class OnScrollRecyclerViewListenerV2(private val layoutManager: LinearLayoutManager,
                                              private val adapter: RecyclerView.Adapter<*>) : RecyclerView.OnScrollListener() {

    private val oldItemPositions = IntArray(2)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        if ((firstCompletelyVisibleItemPosition != -1 && lastCompletelyVisibleItemPosition != -1)
                && (oldItemPositions[0] != firstCompletelyVisibleItemPosition || oldItemPositions[1] != lastCompletelyVisibleItemPosition)) {
            onScrolled(firstCompletelyVisibleItemPosition, lastCompletelyVisibleItemPosition, adapter.itemCount, recyclerView.scrollState)

            oldItemPositions[0] = firstCompletelyVisibleItemPosition
            oldItemPositions[1] = lastCompletelyVisibleItemPosition
        }
    }

    abstract fun onScrolled(firstCompletelyVisibleItemPosition: Int,
                            lastCompletelyVisibleItemPosition: Int,
                            totalItemCount: Int,
                            scrollState: Int)
}
