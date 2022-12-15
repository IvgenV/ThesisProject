package thesis_project.presentation.adapter

import thesis_project.data.data_base.news.News

interface ToFragmentNews {
    fun onClick(news: News)
    fun share(news: News)
}