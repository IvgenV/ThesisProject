package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.MapDescription

class MapDescriptionAdapter : ListAdapter<MapDescription,
        MapDescriptionAdapter.ViewHolder>(MapDescriptionCompareCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_map_description, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.headline.text = getItem(position).headline
        holder.description.text = getItem(position).description
    }

    fun setData(data: List<MapDescription>) {
        submitList(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headline = view.findViewById<TextView>(R.id.map_headline)
        val description = view.findViewById<TextView>(R.id.map_description)
    }
}