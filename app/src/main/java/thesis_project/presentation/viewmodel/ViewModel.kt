package thesis_project.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.db.RateData


class ViewModel : ViewModel() {

    var loaclDB = RateData(App.instance)

    ///инициализируем список string для дальнейшего добавления в него
    ///данных только по долларам
    var listOfDollar = MutableLiveData<List<String>>()
    var rate = MutableLiveData<List<Rate>>()
    ////var listOfDollar = MutableLiveData<MutableList<String>>() почему при такой инициализации
    ///NullPointerException?

    fun initial() {
        viewModelScope.launch {
            ///делаем запрос на апишку, получаем данные, записываем их
            //в локальную базу данных
            val call = Dependencies.apiService.getRateCountry()
            if (call.isSuccessful) {
                loaclDB.addCountryRate(call.body() ?: listOf())
            }
        }
    }

    fun updateCountryRate() {
        ////почему возвращает "default"?

        viewModelScope.launch {
            ///перебираем список из бд, смотрим, если в нашем listOfDollar
            //такого значения (it.usd) нет, то добавляем его
            //возвращаем сформированный список долларов и сетим его в recyclerview
            /// проблема в том, что я в listOfDollar не могу добавить вообще ничего
            ///из корутины т.е. если я например напишу listOfDollar.add(something)
            ///в viewModelScope.launch то ничего не добалвяется, если вне то все ок


            ///так нормально если потом завернуть это ф функцию какую нибудь
            listOfDollar.value = loaclDB.getRateCountry().map { it.usd }.toSet().toList().sortedBy { it }
        }
    }

    fun getCountryRate(): LiveData<List<String>> {
        return listOfDollar
    }

    fun getRate(): LiveData<List<Rate>> {
        ///почему вот это работает? Потому что getRateCountry() suspend?
        viewModelScope.launch {
            rate.value = loaclDB.getRateCountry()
        }
        return rate
    }


}