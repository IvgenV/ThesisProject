package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.ItemDistance

class ItemDistanceAdapter: ListAdapter<ItemDistance,
        ItemDistanceAdapter.ViewHolder>(ItemDistanceCompareCallback()) {

    private var listenerToMap:ToFragmentMap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item_distance,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.text = getItem(position).item
        holder.distance.text = getItem(position).distance.toString()
        val info = getItem(position).item
        holder.itemView.setOnClickListener {
            listenerToMap?.onClick(info)
        }
    }

    fun setData(data: List<ItemDistance>){
        submitList(data)
    }


    fun setListenerToMap(toFragmentMap: ToFragmentMap){
        listenerToMap = toFragmentMap
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val item: TextView = view.findViewById(R.id.itemFilial)
        val distance:TextView = view.findViewById(R.id.itemDistance)
    }


}
