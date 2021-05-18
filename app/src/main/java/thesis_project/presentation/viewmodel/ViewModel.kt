package thesis_project.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thesis_project.Dependencies
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase
import thesis_project.App
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.db.RateData


class ViewModel : ViewModel() {

    val exchange: ExchangeRatesBelarusBankUseCase by lazy { Dependencies.getExchangeRateBelarusBankUseCase() }
    var listBrest = MutableLiveData<List<Rate>>()
    var listMinsk = MutableLiveData<List<Rate>>()
    var rateData = RateData(App.instance)

    fun update() {
        viewModelScope.launch {
            rateData.addRateMinsk(exchange.getRateMinsk())
        }
    }

    fun getMinskList():LiveData<List<Rate>>{
        viewModelScope.launch {
            rateData.addRateMinsk(exchange.getRateMinsk())
            listMinsk.value = rateData.getRateList()
        }
        return listMinsk
    }

    fun getBrestList():LiveData<List<Rate>>{
        viewModelScope.launch {
            rateData.addRateBrest(exchange.getRateBrest())
            listBrest.value = rateData.getRateList()
        }
        return listBrest
    }

}