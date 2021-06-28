package thesis_project.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import com.squareup.picasso.Picasso
import thesis_project.data.data_base.news.News

class NewsAdapter: ListAdapter<News,
        NewsAdapter.ViewHolder>(NewsCompareCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name_ru.text = getItem(position).name_ru
        holder.start_data.text = getItem(position).start_date
        holder.text.text = getItem(position).html_ru
        Picasso.get().load( getItem(position).img).into(holder.img);
    }

    fun setData(data: List<News>){
        submitList(data)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name_ru: TextView = view.findViewById(R.id.name_ru)
        val start_data:TextView = view.findViewById(R.id.start_data)
        val text:TextView = view.findViewById(R.id.news_text)
        val img:ImageView= view.findViewById(R.id.image)
    }
}