package thesis_project.presentation.adapter

import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import thesis_project.data.data_base.news.News

interface ToFragmentNews {
    fun onClick(news: News)
    fun share(news: News)
    fun checkSharedPreferences(card:MaterialCardView, title:String)
    ///fun addToSharedPreferences(title:String)
}