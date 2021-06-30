package thesis_project

import android.content.Context
import thesis_project.data.cloud.atm.AtmCloudSource
import thesis_project.data.cloud.infobox.InfoBoxCloudSource
import thesis_project.data.cloud.rate.RateCloudSource
import thesis_project.data.data_base.atm.AtmDb
import thesis_project.data.data_base.filials.RateDb
import thesis_project.data.data_base.infobox.InfoBoxDb
import thesis_project.domain.entity.*
import thesis_project.domain.repository.*
import thesis_project.domain.use_case.*

object Dependencies {

    private fun getRateDbRepository(context: Context): RateDbRepository {
        return RateDb(context)
    }

    fun getRateDbUseCase(context: Context): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository(context))

    private fun getAtmDbRepository(context: Context):AtmDbRepository{
        return AtmDb(context)
    }

    fun getAtmDbUseCase(context: Context): AtmDbUseCase =
        AtmDbUseCaseImpl(getAtmDbRepository(context))

    private fun getInfoBoxDbRepository(context: Context): InfoBoxDbRepository{
        return InfoBoxDb(context)
    }

    fun getInfoBoxDbUseCase(context: Context): InfoBoxDbUseCase =
        InfoBoxDbUseCaseImpl(getInfoBoxDbRepository(context))

    private val apiRateCloudSource:RateCloudRepository by lazy { RateCloudSource }

    fun getRateCloudUseCase():RateCloudUseCase =
        RateCloudUseCaseImpl(apiRateCloudSource)

    private val apiAtmCloudSource: AtmCloudRepository by lazy { AtmCloudSource }

    fun getAtmCloudUseCase(): AtmCloudUseCase =
        AtmCloudUseCaseImpl(apiAtmCloudSource)

    private val apiInfoBoxCloudSource:InfoBoxCloudRepository by lazy { InfoBoxCloudSource }

    fun getInfoBoxCloudUseCase(): InfoBoxCloudUseCase =
        InfoBoxCloudUseCaseImpl(apiInfoBoxCloudSource)

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

    private fun getNewsDbRepository(): NewsDbRepository {
        return NewsData(App.instance)
    }

    fun getNewsDbUseCase(): NewsDbUseCase =
        NewsDbUseCaseImpl(getNewsDbRepository())

    //SharedPrefenceSwitch
    private val sharedPreferencesSwitch:SharedPreferencesSwitchRepository by lazy { SharedPreferencesSwitch(App.instance) }

    fun getSharedPreferenceSwitch():SharedPreferencesSwitchRepository = sharedPreferencesSwitch

    ///WorkerNotification
    private val workerController: WorkerControllerUseCase by lazy { WorkerControllerUseCaseImpl() }

    fun getMyWorkerController():WorkerControllerUseCase = workerController

}