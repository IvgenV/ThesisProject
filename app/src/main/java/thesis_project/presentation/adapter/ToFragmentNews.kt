package thesis_project.presentation.adapter

import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import thesis_project.data.data_base.news.News

interface ToFragmentNews {
    fun onClick(news: News)
    fun share(text:String)
    fun SharedPreferences(card:MaterialCardView,title:String)
}