package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.ItemAdressDistance

class ItemAddressDistanceCompareCallback : DiffUtil.ItemCallback<ItemAdressDistance>() {
    override fun areItemsTheSame(
        oldItem: ItemAdressDistance,
        newItem: ItemAdressDistance
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ItemAdressDistance,
        newItem: ItemAdressDistance
    ): Boolean {
        return oldItem == newItem
    }

}
