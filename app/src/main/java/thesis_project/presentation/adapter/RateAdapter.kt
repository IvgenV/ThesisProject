package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R

class RateAdapter: ListAdapter<String,
        RateAdapter.ViewHolder>(RateCompareCallback()) {

    private var listenerFR2:ToFragment3? = null
    private var listenerFR3:ToFragment4? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rate.text = getItem(position).toString()
        val info = getItem(position).toString()
        holder.itemView.setOnClickListener {
            if(listenerFR2!=null){
                listenerFR2?.onClick(info)
            }else listenerFR3?.onClick(info)
        }
    }

    fun setData(data: List<String>){
        submitList(data)
    }

    fun setListenerFR2(toFragment3: ToFragment3){
        listenerFR2 = toFragment3
    }

    fun setListenerFR3(toFragment4: ToFragment4){
        listenerFR3 = toFragment4
    }

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val rate: TextView = view.findViewById(R.id.itemRate)
    }


}
