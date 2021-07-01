package thesis_project

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thesis_project.data.cloud.atm.AtmCloudSource
import thesis_project.data.cloud.news.NewsCloudSource
import thesis_project.data.cloud.rate.RateCloudSource
import thesis_project.data.data_base.atm.AtmDbData
import thesis_project.data.data_base.filials.СoordinatesPojo
import thesis_project.data.data_base.filials.RateDbData
import thesis_project.data.data_base.news.NewsData
import thesis_project.data.sharedPreferences.SharedPreferencesSwitch
import thesis_project.data.worker.WorkerControllerUseCaseImpl
import thesis_project.domain.entity.*
import thesis_project.domain.repository.*
import thesis_project.domain.use_case.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object Dependencies {

    private fun getRateDbRepository(context: Context): RateDbRepository {
        return RateDbData(context)
    }

    fun getRateDbUseCase(context: Context): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository(context))

    private fun getAtmDbRepository(context: Context):AtmDbRepository{
        return AtmDbData(context)
    }

    fun getAtmDbUseCase(context: Context): AtmDbUseCase =
        AtmDbUseCaseImpl(getAtmDbRepository(context))


    private val apiRateCloudSource:RateCloudRepository by lazy { RateCloudSource }

    fun getRateCloudUseCase():RateCloudUseCase =
        RateCloudUseCaseImpl(apiRateCloudSource)

    private val apiAtmCloudSource: AtmCloudRepository by lazy { AtmCloudSource }

    fun getAtmCloudUseCase(): AtmCloudUseCase =
        AtmCloudUseCaseImpl(apiAtmCloudSource)

    /*suspend fun <T> Call<T>.await():T = suspendCoroutine { cont->
        enqueue(object : Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    cont.resume(response.body()!!)
                }else cont.resumeWithException(Error("SDsd"))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                cont.resumeWithException(t)
            }

        })
    }*/

    ///News
    private val apiNewsCloudSource: NewsCloudRepository by lazy { NewsCloudSource }

    fun getNewsCloudUseCase(): NewsCloudUseCase =
        NewsCloudUseCaseImpl(apiNewsCloudSource)

    private fun getNewsDbRepository(context: Context): NewsDbRepository {
        return NewsData(context)
    }

    fun getNewsDbUseCase(context: Context): NewsDbUseCase =
        NewsDbUseCaseImpl(getNewsDbRepository(context))

    //SharedPrefenceSwitch
    private val sharedPreferencesSwitch:SharedPreferencesSwitchRepository by lazy { SharedPreferencesSwitch() }

    suspend fun addStatusSwitch(key:String, status:Boolean, context: Context){
        sharedPreferencesSwitch.Add(key, status, context)
    }

    fun takeStatusSwitch(key: String,context: Context):Boolean{
        return sharedPreferencesSwitch.Take(key, context)
    }
    ///WorkerNotification
    private val workerController: WorkerControllerUseCase by lazy { WorkerControllerUseCaseImpl() }

    suspend fun startNotification(){
        workerController.StartWorkerNotificationNews()
    }

    suspend fun stopNotification(){
        workerController.StopWorkerNotificationNews()
    }
}