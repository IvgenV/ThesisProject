package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import java.math.BigDecimal

class RateAdapter : ListAdapter<Double,
        RateAdapter.ViewHolder>(RateCompareCallback()) {

    private var listener:ToFragmentFilials?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rate.text = getItem(position).toString()
        val info = getItem(position).toString()
        holder.itemView.setOnClickListener {
            listener?.onClick(info)
        }
    }

    fun setListenerFr2(toFragmentFilials: ToFragmentFilials){
        listener = toFragmentFilials
    }

    fun setData(data:List<Double>){
        submitList(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rate: TextView = view.findViewById(R.id.itemRate)
    }


}