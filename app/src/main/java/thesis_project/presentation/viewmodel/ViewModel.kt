package thesis_project.presentation.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import thesis_project.*
import thesis_project.data.data_base.filials.RateFilialPojo
import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.SharedPreferencesSwitchRepository
import thesis_project.domain.use_case.WorkerControllerUseCase


class ViewModel : ViewModel() {

    var localRateDb = Dependencies.getRateDbUseCase(App.instance)
    var localAtmDb = Dependencies.getAtmDbUseCase(App.instance)
    var listOfCurrency = MutableLiveData<List<String>>()
    var listOfFilial = MutableLiveData<List<String>>()
    var listRateFilial = MutableLiveData<List<RateFIlial>>()
    var latLng = MutableLiveData<LatLng>()
    //News
    var localNewsDb=Dependencies.getNewsDbUseCase()
    var listNews = MutableLiveData<List<News>>()
    val sharedPreferencesSwitch:SharedPreferencesSwitchRepository by lazy { Dependencies.getSharedPreferenceSwitch() }
    val myWorkerController:WorkerControllerUseCase by lazy { Dependencies.getMyWorkerController() }
    fun initialCountryRate() {
        viewModelScope.launch {
            val callRate = Dependencies.getRateCloudUseCase().getRateCountry()
            val callFilials = Dependencies.getRateCloudUseCase().getFilialsCountry()
            if (callRate.isSuccessful && callFilials.isSuccessful) {
                val dataList = mutableListOf<RateFilialPojo>()
                callRate.body()?.forEach { rate ->
                    callFilials.body()?.forEach { filial ->
                        if (rate.id == filial.id) {
                            val data = RateFilialPojo(
                                rate.usd_in,
                                rate.usd_out,
                                rate.euro_in,
                                rate.euro_out,
                                rate.rub_in,
                                rate.rub_out,
                                rate.uah_in,
                                rate.uah_out,
                                rate.id,
                                rate.filial,
                                rate.home,
                                rate.streetType,
                                rate.street,
                                rate.name,
                                filial.latitude,
                                filial.longitude
                            )
                            dataList.add(data)
                        }
                    }
                }
                localRateDb.addListRate(dataList)
            } else localRateDb.addListRate(listOf())
        }
    }

    fun createListCurrency(location: String, in_out: Int, currency: Int) {

        if (in_out == 0) {
            when (currency) {
                Constnsts.usd -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.usd_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.usd_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.eur -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.euro_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.euro_in }.toSet()
                                    .toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.rub -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.rub_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.rub_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.uah -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.uah_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.uah_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
            }
        }
        if (in_out == 1) {
            when (currency) {
                Constnsts.usd -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.usd_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.usd_out }.toSet()
                                    .toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.eur -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.euro_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.euro_out }.toSet()
                                    .toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.rub -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.rub_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.rub_out }.toSet()
                                    .toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnsts.uah -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localRateDb.getRateCountry().map { it.uah_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localRateDb.getRateCity(location).map { it.uah_out }.toSet()
                                    .toList()
                                    .sortedBy { it }
                        }
                    }
                }
            }
        }

    }


    fun getListCurrency(): LiveData<List<String>> {
        return listOfCurrency
    }


    fun createListFilial(rate: String, in_out: Int, currency: Int, location: Location?) {
        if (rate != "empty" && in_out != -1 && currency != -1&& location!=null) {
            if (in_out == 0) {
                when (currency) {
                    Constnsts.usd -> {
                        viewModelScope.launch {
                            val list = mutableListOf<RateFIlial>()
                            localRateDb.getRateCountry().forEach {
                                if (it.usd_in == rate) {
                                    val loc = Location("")
                                    loc.latitude = it.latitude.toDouble()
                                    loc.longitude = it.longitude.toDouble()
                                    val dist = location.distanceTo(loc)
                                    list.add(RateFIlial(dist, it.filial))
                                }
                            }
                            list.sortBy { it.distance }
                            if (list.size < 15) {
                                listRateFilial.value = list
                            } else {
                                listRateFilial.value = list.take(15)
                            }
                        }
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.usd_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.eur -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.euro_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.rub -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.rub_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.uah -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.uah_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                }
            }
            if (in_out == 1) {
                when (currency) {
                    Constnsts.usd -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.usd_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.eur -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.euro_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.rub -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.rub_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnsts.uah -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localRateDb.getRateCountry().forEach {
                                if (it.uah_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                }
            }
        }
    }


    fun getRatFilials(): LiveData<List<RateFIlial>> {
        return listRateFilial
    }

    fun createGps(filial: String) {
        viewModelScope.launch {
            localRateDb.getRateCountry().forEach {
                if (it.filial == filial) {
                    latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                }
            }
        }
    }

    fun getGps(): LiveData<LatLng> {
        return latLng
    }
    //News
    fun getNews(): LiveData<List<News>>{
        viewModelScope.launch {
            val callNews = Dependencies.getNewsCloudUseCase().getNews()
            if (callNews.isSuccessful){
                localNewsDb.addNews(callNews.body()?: listOf())
            }
            listNews.value=localNewsDb.getNewsList()
        }
        return listNews
    }

    ///SaveStatusSwitch
    fun addStatusSwitch(key:String,status:Boolean){
            sharedPreferencesSwitch.add(key,status)
    }

    fun takeStatusSwitch(key: String):Boolean{
        return sharedPreferencesSwitch.take(key)
    }

    ///Worker

    fun startNotificationNews(){
        viewModelScope.launch {
            myWorkerController.StartWorkerNotificationNews()
        }
    }

    fun stopNotificationNews(){
        viewModelScope.launch {
            myWorkerController.StopWorkerNotificationNews()
        }
    }
}