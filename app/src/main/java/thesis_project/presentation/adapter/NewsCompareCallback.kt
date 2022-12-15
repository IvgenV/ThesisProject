package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.NewsWithChacked

class NewsCompareCallback : DiffUtil.ItemCallback<NewsWithChacked>() {
    override fun areItemsTheSame(oldItem: NewsWithChacked, newItem: NewsWithChacked): Boolean {
        return (oldItem.news.html_ru == newItem.news.html_ru) && (oldItem.news.html_ru == newItem.news.html_ru)
    }

    override fun areContentsTheSame(oldItem: NewsWithChacked, newItem: NewsWithChacked): Boolean {
        return oldItem == newItem
    }

}