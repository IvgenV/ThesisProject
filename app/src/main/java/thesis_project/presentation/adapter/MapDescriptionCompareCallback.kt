package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.MapDescription

class MapDescriptionCompareCallback : DiffUtil.ItemCallback<MapDescription>() {

    override fun areItemsTheSame(oldItem: MapDescription, newItem: MapDescription): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MapDescription, newItem: MapDescription): Boolean {
        return oldItem == newItem
    }

}