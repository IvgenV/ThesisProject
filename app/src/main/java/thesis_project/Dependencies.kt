package thesis_project

import thesis_project.data.cloud.RateCallback
import thesis_project.domain.entity.ExchangeRateBelarusBankImpl
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase

object Dependencies {

    private val exchangeRateBelarusBank: ExchangeRatesBelarusBankRepository by lazy { RateCallback() }

    fun getExchangeRateBelarusBankUseCase(): ExchangeRatesBelarusBankUseCase =
        ExchangeRateBelarusBankImpl(exchangeRateBelarusBank)

}