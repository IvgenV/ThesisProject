package thesis_project.domain.entity


import thesis_project.data.data_base.Rate
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase

class ExchangeRateBelarusBankImpl(
    val exchangeRatesBelarusBankRepository: ExchangeRatesBelarusBankRepository
): ExchangeRatesBelarusBankUseCase {
    override suspend fun getRateMinsk(): List<Rate> {
       return exchangeRatesBelarusBankRepository.getRateMinsk()
    }

    override suspend fun getRateBrest():List<Rate> {
        return exchangeRatesBelarusBankRepository.getRateBrest()
    }

    override fun getRuble(): List<String> {
        return exchangeRatesBelarusBankRepository.getRuble()
    }

}