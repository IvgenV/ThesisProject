package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.ItemDistance

class ItemDistanceCompareCallback: DiffUtil.ItemCallback<ItemDistance>() {
    override fun areItemsTheSame(oldItem: ItemDistance, newItem: ItemDistance): Boolean {
        return (oldItem==newItem)
    }

    override fun areContentsTheSame(oldItem: ItemDistance, newItem: ItemDistance): Boolean {
        return oldItem == newItem
    }

}