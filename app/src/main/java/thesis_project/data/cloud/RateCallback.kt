package thesis_project.data.cloud

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import thesis_project.Rate
import thesis_project.RateList
import thesis_project.domain.repository.ExchangeRatesBelarusBankRepository

class RateCallback : Callback<RateList>, ExchangeRatesBelarusBankRepository {

    var apiService = Cloud.apiService
    var rate = RateList()

    override  fun getRateMinsk(): Call<List<Rate>> {
        return apiService.getRateMinsk()
    }

    override fun getRateBrest(): Call<List<Rate>> {
       /* val call = apiService.getRateMinsk()
        if(call.isSuccessful){

        }
        return list*/
        return apiService.getRateBrest()
    }

    override fun getRuble(): List<String> {
        return listOf()
    }

    override fun onResponse(call: Call<RateList>, response: Response<RateList>) {
        if (response.isSuccessful) {
            rate = response.body()?: RateList()
            Log.d("RESPNSETAG", "onResponse sucess!!")
        } else
            Log.d("RESPNSETAG", "onResponse fail!!")
    }

    override fun onFailure(call: Call<RateList>, t: Throwable) {
        Log.d("RESPNSETAG", "onFailure")
        t.printStackTrace()
    }


}