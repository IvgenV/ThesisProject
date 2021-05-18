package thesis_project.domain.repository

import thesis_project.data.data_base.Rate

interface ExchangeRatesBelarusBankRepository {

    suspend fun getRateMinsk(): List<Rate>
    suspend fun getRateBrest(): List<Rate>
    fun getRuble(): List<String>

}