package thesis_project.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import thesis_project.App
import thesis_project.Constnst
import thesis_project.Dependencies
import thesis_project.data.data_base.Data
import thesis_project.data.data_base.Rate
import kotlin.math.log


class ViewModel : ViewModel() {

    var localDb = Dependencies.getRateDbUseCase(App.instance)
    var listOfCurrency = MutableLiveData<List<String>>()
    var listOfFilial = MutableLiveData<List<String>>()
    var latLng = MutableLiveData<LatLng>()

    fun initialCountryRate() {
        viewModelScope.launch {
            val callRate = Dependencies.getCountryRate()
            val callFilials = Dependencies.getFilialsCountry()
            if (callRate.isSuccessful && callFilials.isSuccessful) {
                val dataList = mutableListOf<Data>()
                callRate.body()?.forEach { rate ->
                    callFilials.body()?.forEach { filial ->
                        if (rate.id == filial.id) {
                            val data = Data(
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
                localDb.addListRate(dataList)
            } else localDb.addListRate(listOf())
        }
    }


    fun createListCurrency(location: String, in_out: Int, currency: Int) {

        if (in_out == 0) {
            when (currency) {
                Constnst.usd -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.usd_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.usd_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.eur -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.euro_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.euro_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.rub -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.rub_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.rub_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.uah -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.uah_in }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.uah_in }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
            }
        }
        if (in_out == 1) {
            when (currency) {
                Constnst.usd -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.usd_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.usd_out }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.eur -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.euro_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.euro_out }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.rub -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.rub_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.rub_out }.toSet().toList()
                                    .sortedBy { it }
                        }
                    }
                }
                Constnst.uah -> {
                    viewModelScope.launch {
                        if (location == "Belarus") {
                            listOfCurrency.value =
                                localDb.getRateCountry().map { it.uah_out }.toSet().toList()
                                    .sortedBy { it }
                        } else {
                            listOfCurrency.value =
                                localDb.getRateCity(location).map { it.uah_out }.toSet().toList()
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


    ///список филиалов в fragment3
    fun createListFilial(rate: String, in_out: Int, currency: Int, location: Location?) {
        if (rate != "empty" && in_out != -1 && currency != -1) {
            if (in_out == 0) {
                when (currency) {
                    Constnst.usd -> {
                        viewModelScope.launch {
                            var list = mutableListOf<Data>()
                            localDb.getRateCountry().forEach { fil ->
                                if(list.size<10){
                                    list.add(fil)
                                }
                                if(list.size>9){
                                    val loc = Location("")
                                    var locList: Location
                                    loc.latitude = fil.latitude.toDouble()
                                    loc.longitude = fil.longitude.toDouble()

                                    ///сдесь ошибка
                                    ///java.util.ConcurrentModificationException
                                    ///at thesis_project.presentation.viewmodel.ViewModel$createListFilial$1.invokeSuspend(ViewModel.kt:331)
                                    list.forEachIndexed { index, data ->
                                        locList = Location("")
                                        locList.latitude = data.latitude.toDouble()
                                        locList.longitude = data.longitude.toDouble()
                                        if(location?.distanceTo(loc)?:1f < location?.distanceTo(locList)?:1f){
                                            list.removeAt(index)
                                            list.add(fil)
                                        }
                                    }
                                }
                            }
                            var liftFilial = mutableListOf<String>()
                            list.forEach {
                                liftFilial.add(it.filial)
                            }
                            listOfFilial.value = liftFilial
                        }
                    }
                    Constnst.eur -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
                                if (it.euro_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnst.rub -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
                                if (it.rub_in == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnst.uah -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
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
                    Constnst.usd -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
                                if (it.usd_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnst.eur -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
                                if (it.euro_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnst.rub -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
                                if (it.rub_out == rate) {
                                    list.add(it.filial)
                                }
                            }
                            listOfFilial.value = list
                        }
                    }
                    Constnst.uah -> {
                        viewModelScope.launch {
                            val list = mutableListOf<String>()
                            localDb.getRateCountry().forEach {
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

    fun getFilials(): LiveData<List<String>> {
        return listOfFilial
    }

    fun createGps(filial: String) {
        viewModelScope.launch {
            localDb.getRateCountry().forEach {
                if (it.filial == filial) {
                    latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    Log.d("DSDSD", latLng.value.toString())
                }
            }
        }
    }

    fun getGps(): LiveData<LatLng> {
        return latLng
    }

}