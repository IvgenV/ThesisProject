package thesis_project.data.cloud

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.Dependencies
import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.NewsBelarusBankRepository
import thesis_project.domain.use_case.DeleteSymbol

class NewsCallback:NewsBelarusBankRepository {
    override suspend fun getNews(): List<News> {
        var list = listOf<News>()
        withContext(Dispatchers.IO){
            if(Dependencies.apiService.getNews().isSuccessful){
                list = Dependencies.apiService.getNews().body()?: listOf()
            }
        }
        val deleteSymbol=DeleteSymbol()
        for (news in  list)
        {
           news.html_ru=deleteSymbol.deleteSymbol(news.html_ru)
        }
        return list
    }

}