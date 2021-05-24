package thesis_project.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.db.RateData


class ViewModel : ViewModel() {

    var loaclDB = RateData(App.instance)


    var listOfDollar = MutableLiveData<List<String>>()
    var listOfEuro = MutableLiveData<List<String>>()
    var rate = MutableLiveData<List<Rate>>()


    fun initial() {
        viewModelScope.launch {

            val call = Dependencies.apiService.getRateCountry()
            if (call.isSuccessful) {
                loaclDB.addCountryRate(call.body() ?: listOf())
            }
        }
    }

    fun updateDollarListRate() {

        viewModelScope.launch {

            listOfDollar.value =
                loaclDB.getRateCountry().map { it.usd }.toSet().toList().sortedBy { it }
        }
    }

    fun updateEuroListRate() {

        viewModelScope.launch {

            listOfEuro.value =
                loaclDB.getRateCountry().map { it.euro_by }.toSet().toList().sortedBy { it }
        }
    }

    fun getCountryRateDollar(): LiveData<List<String>> {
        return listOfDollar
    }

    fun getCountryRateEuro(): LiveData<List<String>> {
        return listOfEuro
    }

}