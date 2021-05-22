package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.data.data_base.Rate

class RateAdapter: ListAdapter<String,
        RateAdapter.ViewHolder>(RateCompareCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rate.text = getItem(position).toString()
    }

    fun setData(data: List<String>){
        submitList(data)
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val rate: TextView = view.findViewById(R.id.itemRate)
    }
}