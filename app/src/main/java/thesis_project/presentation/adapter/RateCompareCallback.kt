package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.data.data_base.Rate

class RateCompareCallback: DiffUtil.ItemCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return (oldItem==newItem)
    }

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem == newItem
    }
}