package thesis_project.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.Data
import thesis_project.data.data_base.Rate


class ViewModel : ViewModel() {

    var localDb = Dependencies.getRateDbUseCase(App.instance)
    var listOfDollar = MutableLiveData<List<String>>()
    var listOfEuro = MutableLiveData<List<String>>()
    var rate = MutableLiveData<List<Rate>>()
    var listOfFilial = MutableLiveData<List<String>>()
    var latLng = MutableLiveData<LatLng>()

    fun initialCountryRate() {
        viewModelScope.launch {
            val callRate = Dependencies.getCountryRate()
            val callFilials  = Dependencies.getFilialsCountry()
            if (callRate.isSuccessful&&callFilials.isSuccessful) {
                val dataList = mutableListOf<Data>()
                callRate.body()?.forEach { rate->
                    callFilials.body()?.forEach { filial->
                        if(rate.id == filial.id){
                            val data = Data(rate.usd,rate.euro_by,rate.id,rate.filial,rate.home,rate.streetType,
                            rate.street,rate.name,filial.latitude,filial.longitude)
                            dataList.add(data)
                        }
                    }
                }
                localDb.addListRate(dataList)
            }else localDb.addListRate(listOf())
        }
    }

    fun initialCityRate(){

    }

    fun updateDollarListRate(location: String) {

        viewModelScope.launch {

            if (location == "Belarus") {
                listOfDollar.value =
                    localDb.getRateCountry().map { it.usd }.toSet().toList().sortedBy { it }
            } else {
                listOfDollar.value =
                    localDb.getRateCity(location).map { it.usd }.toSet().toList()
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

    fun createGps(filial:String){
       viewModelScope.launch {
            localDb.getRateCountry().forEach {
                if(it.filial==filial){
                    latLng.value = LatLng(it.latitude.toDouble(),it.longitude.toDouble())
                }
            }
        }
    }

    fun getGps():LiveData<LatLng>{
        return latLng
    }

}