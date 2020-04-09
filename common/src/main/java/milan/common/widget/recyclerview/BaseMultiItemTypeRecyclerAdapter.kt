package milan.common.widget.recyclerview

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseMultiItemTypeRecyclerAdapter<M : BaseMultiItemTypeRecyclerAdapter.BaseModel>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val models: MutableList<M>

    init {
        models = ArrayList()
    }

    constructor(data: List<M>) : this() {
        models.addAll(data)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    fun setAll(data: Collection<M>) {
        models.clear()
        models.addAll(data)
        notifyDataSetChanged()
    }

    fun setAll(data: Array<M>) {
        models.clear()
        models.addAll(data)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): M {
        return models[position]
    }

    fun getItemPosition(item: M): Int {
        var position = -1
        for (i in models.indices) {
            val model = models[i]
            if (model == item) {
                position = i
                break
            }
        }
        return position
    }

    fun clear() {
        models.clear()
        notifyDataSetChanged()
    }

    interface BaseModel {
        val itemType: Int
    }
}
