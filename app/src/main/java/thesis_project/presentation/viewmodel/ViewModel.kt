package thesis_project.presentation.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import thesis_project.*
import thesis_project.data.data_base.filials.RateFilialData
import thesis_project.sealed.SealedInOut


class ViewModel : ViewModel() {

    var localRateDb = Dependencies.getRateDbUseCase(App.instance)
    var localAtmDb = Dependencies.getAtmDbUseCase(App.instance)
    var localInfoBoxDb = Dependencies.getInfoBoxDbUseCase(App.instance)
    var listOfCurrency = MutableLiveData<List<String>>()
    var listOfFilial = MutableLiveData<List<String>>()
    var listRateFilial = MutableLiveData<List<ItemDistance>>()
    var listAtm = MutableStateFlow<List<ItemDistance>>(listOf())
    var listInfoBOx = MutableLiveData<List<ItemDistance>>()
    var latLng = MutableLiveData<LatLng>()


    ///очень долго собирает лист альтернативы?
    fun initialCountryRate() {
        viewModelScope.launch {
            val callRate = Dependencies.getRateCloudUseCase().getRateCountry()
            val callFilials = Dependencies.getRateCloudUseCase().getFilialsCountry()
            if (callRate.isSuccessful && callFilials.isSuccessful) {
                val dataList = mutableListOf<RateFilialData>()
                callRate.body()?.forEach { rate ->
                    callFilials.body()?.forEach { filial ->
                        if (rate.id == filial.id) {
                            val data = RateFilialData(
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


    fun createListFilial(rate: String, in_out: SealedInOut, currency: Int, location: Location?) {
        if (rate != "empty" && in_out != SealedInOut.Error && currency != -1 && location != null) {
            if (in_out == SealedInOut.In) {
                when (currency) {
                    Constnsts.usd -> {
                        viewModelScope.launch {
                            val list = mutableListOf<ItemDistance>()
                            localRateDb.getRateCountry().forEach {
                                if (it.usd_in == rate) {
                                    val loc = Location("")
                                    loc.latitude = it.latitude.toDouble()
                                    loc.longitude = it.longitude.toDouble()
                                    val dist = location.distanceTo(loc)
                                    list.add(ItemDistance(dist, it.filial))
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
            if (in_out == SealedInOut.Out) {
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


    fun getRatFilials(): LiveData<List<ItemDistance>> {
        return listRateFilial
    }

    fun initialAtm() {
        viewModelScope.launch {
            val callAtm = Dependencies.getAtmCloudUseCase().getAtmCountry()
            if (callAtm.isSuccessful) {
                val list = callAtm.body()
                list?.let { localAtmDb.addListAtm(it) }
            } else {
                localAtmDb.addListAtm(listOf())
            }
        }
    }

    fun createListAtm(location: Location?) {
        if (location != null) {
            viewModelScope.launch {
                val list = mutableListOf<ItemDistance>()
                localAtmDb.getAtmCountry().collect { atmDataList ->
                    atmDataList.forEach {
                        val loc = Location("")
                        loc.latitude = it.latitude.toDouble()
                        loc.longitude = it.longitude.toDouble()
                        val dist = location.distanceTo(loc)
                        list.add(ItemDistance(dist, it.id))
                    }
                    list.sortBy { it.distance }
                    if (list.size < 15) {
                        listAtm.value = list
                    } else {
                        listAtm.value = list.take(15)
                    }
                }
            }
        }
    }

    fun getAtm(): StateFlow<List<ItemDistance>> {
        return listAtm
    }

    fun initialInfoBox() {
        viewModelScope.launch {
            val callAtm = Dependencies.getInfoBoxCloudUseCase().getInfoBoxCountry()
            if (callAtm.isSuccessful) {
                val list = callAtm.body()
                list?.let { localInfoBoxDb.insertListInfoBox(it) }
            } else {
                localInfoBoxDb.insertListInfoBox(listOf())
            }
        }
    }

    fun createListInfoBox(location: Location?) {
        if (location != null) {
            viewModelScope.launch {
                var list = mutableListOf<ItemDistance>()
                localInfoBoxDb.getInfoBoxCountry().forEach {
                    val loc = Location("")
                    loc.latitude = it.latitude.toDouble()
                    loc.longitude = it.longitude.toDouble()
                    val dist = location.distanceTo(loc)
                    list.add(ItemDistance(dist, it.id))
                }
                list.sortBy { it.distance }
                if (list.size < 15) {
                    listInfoBOx.value = list
                } else {
                    listInfoBOx.value = list.take(15)
                }
            }
        }
    }

    fun getInfoBox():LiveData<List<ItemDistance>>{
        return listInfoBOx
    }

    fun createGpsFilial(filial: String) {
        viewModelScope.launch {
            localRateDb.getRateCountry().forEach {
                if (it.filial == filial) {
                    latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                }
            }
        }
    }

    fun createGpsAtm(atm: String) {
        viewModelScope.launch {
            localAtmDb.getAtmCountry().collect{ atmDataList ->
                atmDataList.forEach {
                    if (it.id == atm) {
                        latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    }
                }
            }
        }
    }

    fun createGpsInfoBOx(infoBox:String){
        viewModelScope.launch {
            localInfoBoxDb.getInfoBoxCountry().forEach {
                if(it.id == infoBox){
                    latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                }
            }
        }
    }

    fun getGps(): LiveData<LatLng> {
        return latLng
    }

   /* fun <T> Flow<T>.launchWhenStarted(lifecycleCoroutineScope: LifecycleCoroutineScope){
        lifecycleCoroutineScope.launchWhenStarted {
            this@launchWhenStarted.collect()
        }
    }*/

}