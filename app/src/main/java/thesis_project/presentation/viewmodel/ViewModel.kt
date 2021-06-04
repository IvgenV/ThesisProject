package thesis_project.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.RateDbData


class ViewModel : ViewModel() {

    var localDb = Dependencies.getRateDbUseCase(App.instance)
    var listOfDollar = MutableLiveData<List<String>>()
    var listOfEuro = MutableLiveData<List<String>>()
    var rate = MutableLiveData<List<Rate>>()
    var listOfFilial = MutableLiveData<List<String>>()


    fun initialCityRate(city: String) {
        viewModelScope.launch {

            val call = Dependencies.getRateCity(city)
            if (call.isSuccessful) {
                localDb.addListRate(call.body() ?: listOf())
            }
        }
    }

    fun initialCountryRate() {
        viewModelScope.launch {
            val call = Dependencies.getCountryRate()
            if (call.isSuccessful) {
                localDb.addListRate(call.body() ?: listOf())
            }
        }
    }

    fun updateDollarListRate(city: String) {

        viewModelScope.launch {

            if (city == "Belarus") {
                listOfDollar.value =
                    localDb.getRateCountry().map { it.usd }.toSet().toList().sortedBy { it }
            } else {
                listOfDollar.value =
                    localDb.getRateCity(city).map { it.usd }.toSet().toList()
                        .sortedBy { it }
            }

        }
    }

    fun updateEuroListRate() {

        viewModelScope.launch {

            listOfEuro.value =
                localDb.getRateCountry().map { it.euro_by }.toSet().toList().sortedBy { it }
        }
    }

    fun getCountryRateDollar(): LiveData<List<String>> {
        return listOfDollar
    }

    fun getCountryRateEuro(): LiveData<List<String>> {
        return listOfEuro
    }

    fun createRateListFragment3(rate:String){
        viewModelScope.launch {
            var list = mutableListOf<String>()
            localDb.getRateCountry().forEach {
                if(it.usd == rate){
                    list.add(it.filial)
                }
            }
            listOfFilial.value = list
        }
    }

    fun getFilials():LiveData<List<String>>{
        return listOfFilial
    }

}