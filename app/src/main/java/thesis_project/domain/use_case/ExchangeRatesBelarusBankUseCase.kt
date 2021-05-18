package thesis_project.domain.use_case

import thesis_project.data.data_base.Rate

interface ExchangeRatesBelarusBankUseCase {

    suspend fun getRateMinsk(): List<Rate>
    suspend fun getRateBrest(): List<Rate>
    fun getRuble():List<String>

}