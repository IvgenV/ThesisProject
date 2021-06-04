package thesis_project.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R

class RateAdapter: ListAdapter<String,
        RateAdapter.ViewHolder>(RateCompareCallback()) {

    private var listener:ItemClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rate.text = getItem(position).toString()
        val rate = getItem(position).toString()
        holder.itemView.setOnClickListener {
            listener?.onClick(rate)
        }
    }

    fun setData(data: List<String>){
        submitList(data)
    }

    fun setListener(itemClickListener: ItemClickListener){
        listener = itemClickListener
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val rate: TextView = view.findViewById(R.id.itemRate)
    }


}
