package thesis_project.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import thesis_project.Dependencies
import thesis_project.RateList
import thesis_project.data.cloud.RateCallback
import thesis_project.domain.use_case.ExchangeRatesBelarusBankUseCase
import retrofit2.Callback
import retrofit2.Response
import thesis_project.App
import thesis_project.Rate
import thesis_project.data.cloud.Cloud
import thesis_project.data.db.RateData


class ViewModel: ViewModel() {

    val exchange: ExchangeRatesBelarusBankUseCase by lazy { Dependencies.getExchangeRateBelarusBankUseCase() }
    var listEuro = MutableLiveData<RateList>()
    var listDollar = MutableLiveData<RateList>()
    var listFromDb = MutableLiveData<List<Rate>>()
    private var db = RateData(App.instance)

    fun getDollar():LiveData<RateList>{
       exchange.getRateMinsk().enqueue(object : Callback<List<Rate>>{
           override fun onResponse(call: Call<List<Rate>>, response: Response<List<Rate>>) {
               if(response.isSuccessful){
                   if(listDollar.value != response.body()){
                   }
                   Log.d("VIEWMODEL function","OnResponse!")
               }else Log.d("VIEWMODEL function","Response is not successful!")
           }

           override fun onFailure(call: Call<List<Rate>>, t: Throwable) {
               Log.d("VIEWMODEL function","OnFailure")
           }
       })
        return listDollar
    }

    fun getEuro():LiveData<RateList>{
        exchange.getRateMinsk().enqueue(object : Callback<List<Rate>>{
            override fun onResponse(call: Call<List<Rate>>, response: Response<List<Rate>>) {
                if(response.isSuccessful){
                    if(listEuro.value != response.body()){

                    }
                    Log.d("VIEWMODEL function","OnResponse!")
                }else Log.d("VIEWMODEL function","Response is not successful!")
            }

            override fun onFailure(call: Call<List<Rate>>, t: Throwable) {
                Log.d("VIEWMODEL function","OnFailure")
            }
        })
        return listEuro
    }

    fun init(){
        exchange.getRateMinsk().enqueue(object : Callback<List<Rate>>{
            override fun onResponse(call: Call<List<Rate>>, response: Response<List<Rate>>) {
                if(response.isSuccessful){
                    if(response.isSuccessful){
                        db.addRateList(response.body()?: RateList())
                    }
                    Log.d("VIEWMODEL function","OnResponse!")
                }else Log.d("VIEWMODEL function","Response is not successful!")
            }

            override fun onFailure(call: Call<List<Rate>>, t: Throwable) {
                Log.d("VIEWMODEL function","OnFailure")
            }
        })
    }

    fun getListDollgar():LiveData<List<Rate>>{
       viewModelScope.launch{
            db.getDollarDb().collect {
                listFromDb.value = it
            }
        }
        return listFromDb
    }

}