package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.RateFIlial

class FilialCompareCallback: DiffUtil.ItemCallback<RateFIlial>() {
    override fun areItemsTheSame(oldItem: RateFIlial, newItem: RateFIlial): Boolean {
        return (oldItem==newItem)
    }

    override fun areContentsTheSame(oldItem: RateFIlial, newItem: RateFIlial): Boolean {
        return oldItem == newItem
    }

}