package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.ItemAdressDistance

class ItemAddressDistanceAdapter : ListAdapter<ItemAdressDistance,
        ItemAddressDistanceAdapter.ViewHolder>(ItemAddressDistanceCompareCallback()) {

    private var listenerToMap: ToFragmentMap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_item_distance,
                parent, false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = getItem(position).id
        holder.type.text = getItem(position).type
        holder.address.text = getItem(position).adress
        holder.distance.text = getItem(position).distance.toString()
        val info = getItem(position).id
        holder.itemView.setOnClickListener {
            listenerToMap?.onClick(info)
        }
    }


    fun setData(data: List<ItemAdressDistance>) {
        submitList(data)
    }


    fun setListenerToMap(toFragmentMap: ToFragmentMap) {
        listenerToMap = toFragmentMap
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.itemId)
        val address: TextView = view.findViewById(R.id.itemAdress)
        val distance: TextView = view.findViewById(R.id.itemDistance)
        val type: TextView = view.findViewById(R.id.itemType)
    }

}