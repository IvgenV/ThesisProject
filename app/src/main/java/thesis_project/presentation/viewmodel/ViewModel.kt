package thesis_project.presentation.viewmodel

import android.location.Location
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import thesis_project.*
import thesis_project.data.data_base.atm.AtmData
import thesis_project.data.data_base.filials.RateData
import thesis_project.data.data_base.filials.RateFilialData
import thesis_project.data.data_base.filials.СoordinatesData
import thesis_project.data.data_base.news.News
import thesis_project.sealed.Currency
import thesis_project.sealed.CurrencyOperation
import java.util.*
import thesis_project.domain.use_case.SharedPreferencesNewsUseCase
import thesis_project.domain.use_case.SharedPreferencesRateDoubleUseCase
import thesis_project.domain.use_case.SharedPreferencesSwitchUseCase
import thesis_project.domain.use_case.WorkerControllerUseCase


class ViewModel : ViewModel() {
    val textError = "Ошибка при подключений"
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(App.instance, textError, duration)
    var userKey = ""

    private var localRateDb = Dependencies.getRateDbUseCase(App.instance)
    private var atmBB = Dependencies.getAtmUseCase(App.instance)
    private var localInfoBoxDb = Dependencies.getInfoBoxDbUseCase(App.instance)

    private var listOfCurrency = MutableLiveData<List<Double>>()
    private var listRateFilial = MutableLiveData<List<ItemDistance>>()

    private var listAtmFromDb = MutableStateFlow<List<AtmData>>(listOf())
    private var listAtmDistance = MutableStateFlow<List<ItemAdressDistance>>(listOf())

    private var listInfoBox = MutableLiveData<List<ItemDistance>>()
    private var latLng = MutableLiveData<LatLng>()
    private var infoBoxInfo: String? = null


    //News
    private var localNewsDb = Dependencies.getNewsDbUseCase()
    private var stateNews = MutableLiveData<StateNews>()
    private var listNewsWithChackedLD = MutableLiveData<List<NewsWithChacked>>()

    //profile
    var email = ""
    var name = ""
    var surname = ""

    private var progress = MutableLiveData(View.INVISIBLE)
    private var checkLocation = MutableLiveData(false)

    fun setInfoBoxInfo(atm: String?) {
        this.infoBoxInfo = atm
    }

    fun getInfoBoxInfo(): String? {
        return infoBoxInfo
    }

    //SharedPreferences
    private val sharedPreferencesSwitch: SharedPreferencesSwitchUseCase by lazy { Dependencies.getSharedPreferenceSwitch() }
    private val sharedPreferencesRate: SharedPreferencesRateDoubleUseCase by lazy { Dependencies.getSharedPreferenceRate() }
    private val sharedPreferencesNews: SharedPreferencesNewsUseCase by lazy { Dependencies.getSharedPreferencesNews() }

    //Worker
    private val myWorkerController: WorkerControllerUseCase by lazy { Dependencies.getMyWorkerController() }


    ///rateBlock
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
                progress.value = View.VISIBLE
                val callRate = Dependencies.getRateCloudUseCase().getRateCountry()
                val callFilials = Dependencies.getRateCloudUseCase().getFilialsCountry()
                if (callRate.isSuccessful && callFilials.isSuccessful) {
                    val dataList = mutableListOf<RateFilialData>()
                    callRate.body()?.sortedBy { it.id }
                    callFilials.body()?.sortedBy { it.id }
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
                    progress.value = View.INVISIBLE
                } else {
                    progress.value = View.INVISIBLE
                    localRateDb.addListRate(listOf())
                }
            } catch (e: Exception) {
                progress.value = View.INVISIBLE
                toast.show()
            }

        }
    }

    fun createListCurrency(operation: CurrencyOperation, currency: Int) {
        if (operation is CurrencyOperation.Buy) {
            when (currency) {
                Constnsts.usd -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.usd_in }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.eur -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.euro_in }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.rub -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.rub_in }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.uah -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.uah_in }.toSet().toList()
                                .sortedBy { it }
                    }
                }
            }
        }
        if (operation is CurrencyOperation.Sell) {
            when (currency) {
                Constnsts.usd -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.usd_out }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.eur -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.euro_out }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.rub -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.rub_out }.toSet().toList()
                                .sortedBy { it }
                    }
                }
                Constnsts.uah -> {
                    viewModelScope.launch {
                        listOfCurrency.value =
                            localRateDb.getRateCountry().map { it.uah_out }.toSet().toList()
                                .sortedBy { it }
                    }
                }
            }
        }

    }

    fun getListCurrency(): LiveData<List<Double>> {
        return listOfCurrency
    }


    ///Filial block
    fun createItemDistanceFilials(
        rateFilialData: RateFilialData,
        location: Location
    ): ItemDistance {
        val loc = Location("")
        loc.latitude = rateFilialData.latitude
        loc.longitude = rateFilialData.longitude
        val dist = location.distanceTo(loc)
        return ItemDistance(dist, rateFilialData.filial)
    }

    fun updatesFilials(location: Location, compare: (RateFilialData) -> Boolean) {

        viewModelScope.launch {
            val list = mutableListOf<ItemDistance>()
            localRateDb.getRateCountry().forEach {
                if (compare.invoke(it)) {
                    list.add(createItemDistanceFilials(it, location))
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


    ////Atm block
    fun initialAtmCloud(location: Location?) {
        if (location != null) {
            viewModelScope.launch {
                try {
                    progress.value = View.VISIBLE
                    delay(300)
                    val call = atmBB.getAtmCountryCloud()
                    if (call.isSuccessful) {
                        call.body()?.let { atmBB.addListAtmDb(it) }
                        createlistAtmDistance(location)
                        progress.value = View.INVISIBLE
                    }

                    if (!call.isSuccessful) {
                        createlistAtmDistance(location)
                        progress.value = View.INVISIBLE
                    }


                } catch (e: Exception) {
                    createlistAtmDistance(location)
                    progress.value = View.INVISIBLE
                    toast.show()
                }
            }
        }
    }

    fun initialAtmDb() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                atmBB.getAtmCountryDb().collect {
                    listAtmFromDb.value = it
                }
            }
        }
    }

    fun createlistAtmDistance(location: Location?) {
        if (location != null) {

            val list = mutableListOf<ItemAdressDistance>()


            listAtmFromDb.value.forEach {

                val loc = Location("")
                loc.latitude = it.latitude
                loc.longitude = it.longitude
                val dist = location.distanceTo(loc).toDouble()
                if (it.addressType == "тракт") {
                    list.add(
                        ItemAdressDistance(
                            "Банкомат",
                            it.id, it.address + " " + it.addressType + " " +
                                    it.house, dist
                        )
                    )
                } else {
                    list.add(
                        ItemAdressDistance(
                            "Банкомат", it.id, it.addressType + " " +
                                    it.address + " " + it.house, dist
                        )
                    )
                }
            }
            list.sortBy { it.distance }
            list.forEach {
                if (it.distance > 1000) {
                    val final = it.distance / 1000
                    it.distance = String.format("%.2f", final).toDouble()
                } else {
                    val final = it.distance
                    it.distance = String.format("%.0f", final).toDouble()
                }
            }
            if (list.size < 15) {
                listAtmDistance.value = list
            } else {
                listAtmDistance.value = list.take(15)
            }
            checkLocation.value = true

        }

        if (location == null) {
            checkLocation.value = false
            Log.d("Locationnullornot", "null!")
        }
    }

    fun getAtm(): StateFlow<List<ItemAdressDistance>> {
        Log.d("atmScope", "getAtm")
        return listAtmDistance
    }


    /////InfoBox block
    private suspend fun createListInfoBox(location: Location) {
        val list = mutableListOf<ItemDistance>()
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
    }

    fun initialInfoBox(location: Location?) {
        viewModelScope.launch {
            progress.value = View.VISIBLE
            delay(300)
            try {
                val callInfoBox = Dependencies.getInfoBoxCloudUseCase().getInfoBoxCountry()
                if (callInfoBox.isSuccessful && location != null) {
                    callInfoBox.body()?.let { localInfoBoxDb.insertListInfoBox(it) }
                    createListInfoBox(location)
                    progress.value = View.GONE
                }
                if (!callInfoBox.isSuccessful && location != null) {
                    delay(300)
                    createListInfoBox(location)
                    progress.value = View.GONE

                }
            } catch (e: Exception) {
                toast.show()
                progress.value = View.GONE
            }
        }
    }

    fun getInfoBox(): LiveData<List<ItemDistance>> {
        return listInfoBox
    }

    ////Gps block
    fun createGpsFilial(filial: String) {
        viewModelScope.launch {
            localRateDb.getRateCountry().forEach {
                if (it.filial == filial) {
                    latLng.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    fun createGpsAtm(atm: String) {
        viewModelScope.launch {
            atmBB.getAtmCountryDb().collect { atmDataList ->
                atmDataList.forEach {
                    if (it.id == atm) {
                        latLng.value =
                            LatLng(it.latitude, it.longitude)
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

    fun getProgress(): LiveData<Int> {
        return progress
    }

    fun getCheckLocation(): LiveData<Boolean> {
        return checkLocation
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

    //////////////////News

    fun getNews(): LiveData<List<NewsWithChacked>> {
        viewModelScope.launch {
            try {
                listNewsWithChackedLD.value = convert(localNewsDb.getNewsList())
                progress.value = View.VISIBLE
                val callNews = Dependencies.getNewsCloudUseCase().getNews()
                if (callNews.isSuccessful) {
                    val listNews = callNews.body() ?: listOf()
                    localNewsDb.addNews(listNews)
                    listNewsWithChackedLD.value = convert(listNews)
                }
                progress.value = View.INVISIBLE
            } catch (e: Exception) {
                progress.value = View.INVISIBLE
                toast.show()
            }
        }
        return listNewsWithChackedLD
    }

    private fun convert(list: List<News>): List<NewsWithChacked> {
        return list.map {
            val isChecked =
                sharedPreferencesNews.check(it.name_ru, userKey)
            NewsWithChacked(it, isChecked)
        }
    }

    fun markNewsAsChecked(title: String) {
        sharedPreferencesNews.add(title, userKey)
    }

    fun setStateNews(stateNews:StateNews){
        this.stateNews.value = stateNews
    }

    fun getStateNews():LiveData<StateNews>{
        return stateNews
    }

}