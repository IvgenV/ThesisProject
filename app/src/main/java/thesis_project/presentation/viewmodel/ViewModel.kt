package thesis_project.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thesis_project.Dependencies
import thesis_project.RateList
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase
import thesis_project.App
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.db.RateData


class ViewModel : ViewModel() {

    val exchange: ExchangeRatesBelarusBankUseCase by lazy { Dependencies.getExchangeRateBelarusBankUseCase() }
    var listEuro = MutableLiveData<RateList>()
    var listDollar = MutableLiveData<RateList>()
    var listFromDb = MutableLiveData<List<Rate>>()
    var rateData = RateData(App.instance)



    fun update() {
        viewModelScope.launch {
            rateData.addRateList(exchange.getRateMinsk())
        }
    }


    fun getDbList(): LiveData<List<Rate>> {
        viewModelScope.launch {
            listFromDb.value = rateData.getMinskRate()
        }
        return listFromDb
    }

}