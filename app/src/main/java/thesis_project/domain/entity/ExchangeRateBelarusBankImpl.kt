package thesis_project.domain.entity


import retrofit2.Call
import thesis_project.Rate
import thesis_project.RateList
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase

class ExchangeRateBelarusBankImpl(
    val exchangeRatesBelarusBankRepository: ExchangeRatesBelarusBankRepository
): ExchangeRatesBelarusBankUseCase {
    override  fun getRateMinsk(): Call<List<Rate>> {
       return exchangeRatesBelarusBankRepository.getRateMinsk()
    }

    override  fun getRateBrest(): Call<List<Rate>> {
        return exchangeRatesBelarusBankRepository.getRateBrest()
    }

    override fun getRuble(): List<String> {
        return exchangeRatesBelarusBankRepository.getRuble()
    }

}