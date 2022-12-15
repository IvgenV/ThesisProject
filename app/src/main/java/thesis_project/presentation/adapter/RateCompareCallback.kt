package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class RateCompareCallback : DiffUtil.ItemCallback<Double>() {
    override fun areItemsTheSame(oldItem: Double, newItem: Double): Boolean {
        return (oldItem == newItem)
    }

    override fun areContentsTheSame(oldItem: Double, newItem: Double): Boolean {
        return oldItem == newItem
    }

}