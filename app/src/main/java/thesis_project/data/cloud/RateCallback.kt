package thesis_project.data.cloud

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.Dependencies.apiService
import thesis_project.data.data_base.Rate
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository

class RateCallback : ExchangeRatesBelarusBankRepository {

    override suspend fun getRateMinsk(): List<Rate> {
        var list = listOf<Rate>()
        withContext(Dispatchers.IO){
            if(apiService.getRateMinsk().isSuccessful){
                list = apiService.getRateMinsk().body()?: listOf()
            }
        }
        return list
    }

    override suspend fun getRateBrest(): List<Rate> {
        var list = listOf<Rate>()
        withContext(Dispatchers.IO){
            if(apiService.getRateBrest().isSuccessful){
                list = apiService.getRateMinsk().body()?: listOf()
            }
        }
        return list
    }

    override fun getRuble(): List<String> {
        return listOf()
    }

}