package thesis_project.domain.use_case

import thesis_project.data.data_base.news.News

interface NewsBelarusBankUseCase {
    suspend fun getNews(): List<News>
}