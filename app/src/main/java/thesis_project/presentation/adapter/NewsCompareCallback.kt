package thesis_project.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import thesis_project.data.data_base.news.News

class NewsCompareCallback: DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return (oldItem.name_ru==newItem.name_ru)&&(oldItem.start_date==newItem.start_date)
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

}