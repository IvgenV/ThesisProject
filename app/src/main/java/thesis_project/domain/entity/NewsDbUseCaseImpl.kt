package thesis_project.domain.entity

import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.NewsDbRepository
import thesis_project.domain.use_case.NewsDbUseCase

class NewsDbUseCaseImpl(val newsDbRepository: NewsDbRepository):NewsDbUseCase {
    override suspend fun getNewsList(): List<News> {
        return  newsDbRepository.getNewsList()
    }

    override suspend fun addNews(newsList: List<News>) {
        return newsDbRepository.addNews(newsList)
    }

}