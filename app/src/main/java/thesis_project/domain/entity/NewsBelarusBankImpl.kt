package thesis_project.domain.entity

import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.NewsBelarusBankRepository
import thesis_project.domain.use_case.NewsBelarusBankUseCase

class NewsBelarusBankImpl(val newsBelarusBankRepository: NewsBelarusBankRepository
): NewsBelarusBankUseCase{
    override suspend fun getNews(): List<News> {
        return newsBelarusBankRepository.getNews()
    }

}