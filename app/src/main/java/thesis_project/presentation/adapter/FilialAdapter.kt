package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.RateFIlial

class FilialAdapter: ListAdapter<RateFIlial,
        FilialAdapter.ViewHolder>(FilialCompareCallback()) {

    private var listenerFR3:ToFragmentMap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate_filial,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rate.text = getItem(position).rate
        holder.distance.text = getItem(position).distance.toString()
        val info = getItem(position).rate
        holder.itemView.setOnClickListener {
            listenerFR3?.onClick(info)
        }
    }

    fun setData(data: List<RateFIlial>){
        submitList(data)
    }


    fun setListenerFR3(toFragmentMap: ToFragmentMap){
        listenerFR3 = toFragmentMap
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val rate: TextView = view.findViewById(R.id.itemFilial)
        val distance:TextView = view.findViewById(R.id.itemDistance)
    }


}
