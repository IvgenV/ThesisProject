package thesis_project.domain.repository

import thesis_project.data.data_base.news.News

interface NewsBelarusBankRepository {
    suspend fun getNews(): List<News>
}