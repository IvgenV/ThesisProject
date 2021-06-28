package thesis_project.domain.entity

import retrofit2.Response
import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.NewsCloudRepository
import thesis_project.domain.use_case.NewsCloudUseCase

class NewsCloudUseCaseImpl(val newsCloudRepository: NewsCloudRepository):NewsCloudUseCase {
    override suspend fun getNews(): Response<List<News>> {
        return newsCloudRepository.getNews()
    }
}