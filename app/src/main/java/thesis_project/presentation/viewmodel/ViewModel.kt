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

    var rateData = RateData(App.instance)
    ///инициализируем список string для дальнейшего добавления в него
    ///данных только по долларам
    var rate = MutableLiveData<List<Rate>>()
    var listOfDollar = MutableLiveData(mutableListOf("Default"))
    ////var list = MutableLiveData<MutableList<String>>() почему при такой инициализации
    ///NullPointerException?

    fun update() {
        viewModelScope.launch {
            ///делаем запрос на апишку, получаем данные, записываем их
            //в локальную базу данных
            if(Dependencies.apiService.getRateCountry().isSuccessful){
                rateData.addRateList(Dependencies.apiService.
                getRateCountry().body()?: listOf())
            }
        }
    }

    fun getCountryRate(): LiveData<MutableList<String>> {
        ////почему возвращает "default"?

        viewModelScope.launch {
            ///перебираем список из бд, смотрим, если в нашем listOfDollar
            //такого значения (it.usd) нет, то добавляем его
            //возвращаем сформированный список долларов и сетим его в recyclerview
            /// проблема в том, что я в listOfDollar не могу добавить вообще ничего
            ///из корутины т.е. если я например напишу listOfDollar.add(something)
            ///в viewModelScope.launch то ничего не добалвяется, если вне то все ок
             rateData.getRateCountry().forEach {
                if(!listOfDollar.value?.contains(it.usd)!!){
                    listOfDollar.value!!.add(it.usd)
                }
            }
            listOfDollar.value?.add("In corutine")
        }
        listOfDollar.value?.add("Out of Corutine ")
        return listOfDollar
    }


}