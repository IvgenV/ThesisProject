package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.data.data_base.Rate

class RateCompareCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return (oldItem==newItem)
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}