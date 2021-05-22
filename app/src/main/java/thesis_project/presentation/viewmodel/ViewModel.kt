package thesis_project.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.db.RateData


class ViewModel : ViewModel() {

    var listRate = MutableLiveData<List<Rate>>()
    var rateData = RateData(App.instance)
    var list = MutableLiveData(mutableListOf("Default"))
    ////var list = MutableLiveData<MutableList<String>>() почему при такой инициализации
    ///NullPointerException?

    fun update() {
        viewModelScope.launch {
            if(Dependencies.apiService.getRateCountry().isSuccessful){
                rateData.addRateList(Dependencies.apiService.
                getRateCountry().body()?: listOf())
            }
        }
    }


   /* fun getMinskList(): LiveData<List<Rate>> {

    }*/

    fun getBrestList(): LiveData<List<Rate>> {
        viewModelScope.launch {
            listRate.value = rateData.getRateBrest()
        }
        return listRate
    }

    fun getCountryRate(): LiveData<MutableList<String>> {
        ////почему возвращает "default"?
        viewModelScope.launch {
            listRate.value = rateData.getRateCountry()
            listRate.value?.forEach {
                if(!list.value?.contains(it.usd)!!){
                    list.value?.add(it.usd)
                }
            }
        }
        return list
    }

}