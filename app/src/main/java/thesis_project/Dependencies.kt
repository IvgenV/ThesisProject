 package thesis_project

import android.content.Context
import kotlinx.coroutines.Dispatchers
import thesis_project.data.cloud.atm.AtmCloudSource
import thesis_project.data.cloud.infobox.InfoBoxCloudSource
import thesis_project.data.cloud.news.NewsCloudSource
import thesis_project.data.cloud.rate.RateCloudSource
import thesis_project.data.data_base.atm.AtmDb
import thesis_project.data.data_base.filials.RateDb
import thesis_project.data.data_base.infobox.InfoBoxDb
import thesis_project.data.data_base.news.NewsData
import thesis_project.data.sharedPreferences.SharedPreferencesRateDouble
import thesis_project.data.sharedPreferences.SharedPreferencesSwitch
import thesis_project.data.worker.WorkerControllerUseCaseImpl
import thesis_project.domain.entity.*
import thesis_project.domain.repository.*
import thesis_project.domain.use_case.*

object Dependencies {

    private lateinit var rateDb: RateDb

    private fun getRateDbRepository(): RateDbRepository {
        return rateDb
    }

    fun getRateDbUseCase(): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository())


    private lateinit var atmDb: AtmDb
    private fun getAtmDbRepository(): AtmDbRepository {
        return atmDb
    }

    fun getAtmDbUseCase(): AtmDbUseCase =
        AtmDbUseCaseImpl(getAtmDbRepository())

    private lateinit var infoBoxDb: InfoBoxDb
    private fun getInfoBoxDbRepository(): InfoBoxDbRepository {
        return infoBoxDb
    }

    fun getInfoBoxDbUseCase(): InfoBoxDbUseCase =
        InfoBoxDbUseCaseImpl(getInfoBoxDbRepository())

    private val apiRateCloudSource: RateCloudRepository by lazy { RateCloudSource }

    fun getRateCloudUseCase(): RateCloudUseCase =
        RateCloudUseCaseImpl(apiRateCloudSource)

    private val apiAtmCloudSource: AtmCloudRepository by lazy { AtmCloudSource(Dispatchers.IO) }

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
    private lateinit var newsData: NewsData

    fun getNewsCloudUseCase(): NewsCloudUseCase =
        NewsCloudUseCaseImpl(apiNewsCloudSource)

    private fun getNewsDbRepository(): NewsDbRepository {
        return newsData
    }

    fun getNewsDbUseCase(): NewsDbUseCase =
        NewsDbUseCaseImpl(getNewsDbRepository())

    //SharedPrefenceSwitch
    private lateinit var sharedPreferencesSwitch: SharedPreferencesSwitchRepository //by lazy { SharedPreferencesSwitch(App.instance) }

    fun getSharedPreferenceSwitch(): SharedPreferencesSwitchUseCase =
        SharedPreferencesSwitchUseCaseImpl(
            sharedPreferencesSwitch
        )

    //SharedPrefence
    private lateinit var sharedPreferencesRateDouble: SharedPreferencesRateDoubleRepository //by lazy { SharedPreferencesRateDouble(App.instance) }

    fun getSharedPreferenceRate(): SharedPreferencesRateDoubleUseCase =
        SharedPreferencesRateDoubleUseCaseImpl(
            sharedPreferencesRateDouble
        )

    ///WorkerNotification
    private lateinit var workerController: WorkerControllerUseCase// by lazy { WorkerControllerUseCaseImpl(App.instance) }

    fun getMyWorkerController(): WorkerControllerUseCase = workerController

    fun init(context: Context) {
        workerController = WorkerControllerUseCaseImpl(context)
        sharedPreferencesRateDouble = SharedPreferencesRateDouble(context)
        sharedPreferencesSwitch = SharedPreferencesSwitch(context)
        newsData = NewsData(context)
        rateDb = RateDb(context)
        atmDb = AtmDb(context)
        infoBoxDb = InfoBoxDb(context)
    }

}