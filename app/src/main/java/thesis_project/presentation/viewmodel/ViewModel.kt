package thesis_project.presentation.viewmodel

import android.location.Location
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import thesis_project.*
import thesis_project.data.data_base.filials.RateData
import thesis_project.data.data_base.filials.RateFilialData
import thesis_project.data.data_base.filials.СoordinatesData
import thesis_project.sealed.Currency
import thesis_project.sealed.CurrencyOperation
import java.util.*
import thesis_project.data.data_base.news.News
import thesis_project.domain.repository.SharedPreferencesSwitchRepository
import thesis_project.domain.use_case.SharedPreferencesRateDoubleUseCase
import thesis_project.domain.use_case.SharedPreferencesSwitchUseCase
import thesis_project.domain.use_case.WorkerControllerUseCase


class ViewModel : ViewModel() {
    val textError = "Ошибка при подключений"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(App.instance, textError, duration)
    private var localRateDb = Dependencies.getRateDbUseCase(App.instance)
    private var localAtmDb = Dependencies.getAtmDbUseCase(App.instance)
    private var localInfoBoxDb = Dependencies.getInfoBoxDbUseCase(App.instance)
    private var listOfCurrency = MutableLiveData<List<Double>>()
    private var listRateFilial = MutableLiveData<List<ItemDistance>>()
    private var listAtm = MutableStateFlow<List<ItemDistance>>(listOf())
    private var listInfoBox = MutableLiveData<List<ItemDistance>>()
    private var latLng = MutableLiveData<LatLng>()
    private var infoBoxInfo: String? = null
    //News
    private var localNewsDb = Dependencies.getNewsDbUseCase()
    private var listNews = MutableLiveData<List<News>>()

    //profile
     var email = ""
     var name = ""
    var surname = ""


    private var progress = MutableLiveData(View.GONE)

    fun setInfoBoxInfo(atm: String?) {
        this.infoBoxInfo = atm
    }

    fun getInfoBoxInfo(): String? {
        return infoBoxInfo
    }
    //SharedPreferences
    val sharedPreferencesSwitch: SharedPreferencesSwitchUseCase by lazy { Dependencies.getSharedPreferenceSwitch() }
    val sharedPreferencesRate: SharedPreferencesRateDoubleUseCase by lazy { Dependencies.getSharedPreferenceRate() }

    //Worker
    val myWorkerController: WorkerControllerUseCase by lazy { Dependencies.getMyWorkerController() }

    fun toRateFilialData(rate: RateData, coordinates: СoordinatesData): RateFilialData {
        return RateFilialData(
            rate.usd_in.toDouble(),
            rate.usd_out.toDouble(),
            rate.euro_in.toDouble(),
            rate.euro_out.toDouble(),
            rate.rub_in.toDouble(),
            rate.rub_out.toDouble(),
            rate.uah_in.toDouble(),
            rate.uah_out.toDouble(),
            rate.id.toInt(),
            rate.filial,
            rate.home,
            rate.streetType,
            rate.street,
            rate.name,
            coordinates.latitude.toDouble(),
            coordinates.longitude.toDouble()
        )
    }


    fun initialCountryRate() {

        viewModelScope.launch {
            try {
                val callRate = Dependencies.getRateCloudUseCase().getRateCountry()
                val callFilials = Dependencies.getRateCloudUseCase().getFilialsCountry()

                if (callRate.isSuccessful && callFilials.isSuccessful) {

                    val dataList = mutableListOf<RateFilialData>()
                    callRate.body()?.forEach { rate ->
                        callFilials.body()?.forEach inner@{ filial ->
                            if (rate.id == filial.id) {
                                val data = toRateFilialData(rate, filial)
                                dataList.add(data)
                                return@inner
                            }
                        }
                    }

                    localRateDb.addListRate(dataList)
                } else localRateDb.addListRate(listOf())
            } catch (e: Exception) {
                toast.show()
            }

        }
    }

    fun createCurrency(location: String) {
        viewModelScope.launch {
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

    }

    fun createListCurrency(location: String, operation: CurrencyOperation, currency: Int) {

        if (operation is CurrencyOperation.Buy) {
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
        if (operation is CurrencyOperation.Sell) {
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


    fun getListCurrency(): LiveData<List<Double>> {
        return listOfCurrency
    }

    fun createItemDistance(rateFilialData: RateFilialData, location: Location): ItemDistance {
        val loc = Location("")
        loc.latitude = rateFilialData.latitude.toDouble()
        loc.longitude = rateFilialData.longitude.toDouble()
        val dist = location.distanceTo(loc)
        return ItemDistance(dist, rateFilialData.filial)
    }

    fun updatesFilials(location: Location, compare: (RateFilialData) -> Boolean) {

        viewModelScope.launch {
            val list = mutableListOf<ItemDistance>()
            localRateDb.getRateCountry().forEach {
                if (compare.invoke(it)) {
                    list.add(createItemDistance(it, location))
                }
            }
            list.sortBy { it.distance }
            if (list.size < 15) {
                listRateFilial.value = list
            } else {
                listRateFilial.value = list.take(15)
            }
        }
    }


    fun createListFilial(
        rate: Double,
        buyOrSell: CurrencyOperation,
        currency: Currency,
        location: Location?
    ) {
        if (rate != Constnsts.empty && buyOrSell != CurrencyOperation.CurrencyOperationError &&
            currency != Currency.CurrencyError && location != null
        ) {
            if (buyOrSell == CurrencyOperation.Buy) {
                when (currency) {
                    is Currency.Dollar -> {
                        updatesFilials(location) {
                            it.usd_in == rate
                        }
                    }
                    Currency.Euro -> {
                        updatesFilials(location) {
                            it.euro_in == rate
                        }
                    }
                    Currency.Rubble -> {
                        updatesFilials(location) {
                            it.rub_in == rate
                        }
                    }
                    Currency.Hryvnia -> {
                        updatesFilials(location) {
                            it.uah_in == rate
                        }
                    }
                }
            }
            if (buyOrSell == CurrencyOperation.Sell) {
                when (currency) {
                    Currency.Dollar -> {
                        updatesFilials(location) {
                            it.usd_out == rate
                        }
                    }
                    Currency.Euro -> {
                        updatesFilials(location) {
                            it.euro_out == rate
                        }
                    }
                    Currency.Rubble -> {
                        updatesFilials(location) {
                            it.rub_out == rate
                        }
                    }
                    Currency.Hryvnia -> {
                        updatesFilials(location) {
                            it.uah_out == rate
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
            try {
                val call = Dependencies.getAtmCloudUseCase().getAtmCountry()
                if (call.isSuccessful) {
                    call.body()?.let { localAtmDb.addListAtm(it) }
                } else {
                    localAtmDb.addListAtm(listOf())
                }
            } catch (e: Exception) {
                toast.show()
            }
        }
    }

    fun createListAtm(location: Location?) {
        if (location != null) {
            viewModelScope.launch {
                progress.value = View.VISIBLE
                Log.d("createListAtm", progress.value.toString())
                delay(500)
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
                    progress.value = View.GONE
                    ///трижды вызывается этот лог?
                    Log.d("createListAtm", progress.value.toString())
                }
            }
        }
    }

    fun getAtm(): StateFlow<List<ItemDistance>> {
        return listAtm
    }


    fun initialInfoBox() {
        viewModelScope.launch {
            try {
                val callAtm = Dependencies.getInfoBoxCloudUseCase().getInfoBoxCountry()
                if (callAtm.isSuccessful) {
                    callAtm.body().let { it?.let { it1 -> localInfoBoxDb.insertListInfoBox(it1) } }
                } else {
                    localInfoBoxDb.insertListInfoBox(listOf())
                }
            } catch (e: Exception) {
                toast.show()
            }
        }
    }

    fun createListInfoBox(location: Location?) {
        if (location != null) {
            viewModelScope.launch {
                progress.value = View.VISIBLE
                delay(500)
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
                    listInfoBox.value = list
                } else {
                    listInfoBox.value = list.take(15)
                }
                progress.value = View.GONE
            }
        }
    }

    fun getProgress(): LiveData<Int> {
        return progress
    }

    fun getInfoBox(): LiveData<List<ItemDistance>> {
        return listInfoBox
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
            localAtmDb.getAtmCountry().collect { atmDataList ->
                atmDataList.forEach {
                    if (it.id == atm) {
                        latLng.value =
                            LatLng(
                                it.latitude.toDouble(),
                                it.longitude.toDouble()
                            )

                    }
                }
            }
        }
    }

    fun createGpsInfoBOx(infoBox: String) {
        viewModelScope.launch {
            localInfoBoxDb.getInfoBoxCountry().forEach {
                if (it.id == infoBox) {
                    latLng.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                }
            }
        }
    }

    fun getGps(): LiveData<LatLng> {
        return latLng
    }

    //News
    fun getNews(): LiveData<List<News>> {
        viewModelScope.launch {
            try {

                val callNews = Dependencies.getNewsCloudUseCase().getNews()
                if (callNews.isSuccessful) {
                    localNewsDb.addNews(callNews.body() ?: listOf())
                }
            } catch (e: Exception) {
                toast.show()
            }
            listNews.value = localNewsDb.getNewsList()
        }
        return listNews
    }

    ///SaveStatusSwitch
    fun addStatusSwitch(key: String, status: Boolean) {
        sharedPreferencesSwitch.add(key, status)
    }

    fun takeStatusSwitch(key: String): Boolean {
        return sharedPreferencesSwitch.take(key)
    }

    ////SaveRateinNotificationSetting
    fun addRateSharedPreferences(key: String, rate: Double) {
        sharedPreferencesRate.add(key, rate)
    }

    fun takeRateSharedPreferences(key: String): Double {
        return sharedPreferencesRate.take(key)
    }

    ///Worker

    fun startNotificationNews() {
        viewModelScope.launch {
            myWorkerController.StartWorkerNotificationNews()
        }
    }

    fun stopNotificationNews() {
        viewModelScope.launch {
            myWorkerController.StopWorkerNotificationNews()
        }
    }

    fun startNotificationRate() {
        viewModelScope.launch {
            myWorkerController.StartWorkerNotificationRate()
        }
    }

    fun stopNotificationRate() {
        viewModelScope.launch {
            myWorkerController.StopWorkerNotificationRate()
        }
    }

}