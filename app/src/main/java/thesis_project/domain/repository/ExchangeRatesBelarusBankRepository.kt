package thesis_project.domain.repository

import retrofit2.Call
import retrofit2.Response
import thesis_project.Rate
import thesis_project.RateList

interface ExchangeRatesBelarusBankRepository {

     fun getRateMinsk(): Call<List<Rate>>
     fun getRateBrest(): Call<List<Rate>>
    fun getRuble(): List<String>

}