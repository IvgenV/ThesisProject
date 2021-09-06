package thesis_project.presentation.adapter

import android.animation.LayoutTransition
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import thesis_project.data.data_base.news.News

class NewsAdapter: ListAdapter<News,
        NewsAdapter.ViewHolder>(NewsCompareCallback()) {

    private var listener: ToFragmentNews? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item_for_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = "${getItem(position).name_ru} sdsdsdsd sdsdsdsdsd sdsdsdsd" +
                "sdsdsdsd dsdsdsds sdsdsd"
        holder.start_data.text = getItem(position).start_date

        val item = getItem(position)

        Picasso.get().load(item.img).into(holder.image)

        holder.card.setOnClickListener {
            ///listener?.addToSharedPreferences(getItem(position).name_ru)
            listener?.onClick(item)
        }

        holder.share.setOnClickListener {
            listener?.share(item)
        }
        listener?.checkSharedPreferences(holder.card,item.name_ru)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setListener(toFragmentNews: ToFragmentNews) {
        listener = toFragmentNews
    }

    fun setData(data: List<News>) {
        submitList(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val start_data: TextView = view.findViewById(R.id.textDate)
        val card: MaterialCardView = view.findViewById(R.id.card)
        val share:ImageView = view.findViewById(R.id.imageShare)
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val image:ImageView = view.findViewById(R.id.image)
    }
}