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
import thesis_project.data.sharedPreferences.SharedPreferencesNews
import thesis_project.data.sharedPreferences.SharedPreferencesRateDouble
import thesis_project.data.sharedPreferences.SharedPreferencesSwitch
import thesis_project.data.worker.WorkerControllerUseCaseImpl
import thesis_project.domain.entity.*
import thesis_project.domain.repository.*
import thesis_project.domain.use_case.*

object Dependencies {

    ////////rate
    private fun getRateDbRepository(context: Context): RateDbRepository {
        return RateDb(context)
    }

    fun getRateDbUseCase(): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository())

    private val apiRateCloudSource:RateCloudRepository by lazy { RateCloudSource }

    fun getRateCloudUseCase():RateCloudUseCase =
        RateCloudUseCaseImpl(apiRateCloudSource)


    /////atm
    private fun getAtmDbRepository(context: Context):AtmDbRepository{
        return AtmDb(context)
    }

    private val apiAtmCloudSource: AtmCloudRepository by lazy { AtmCloudSource(Dispatchers.IO) }

    fun getAtmUseCase(): AtmUseCase =
        AtmUseCaseImpl(getAtmDbRepository(), apiAtmCloudSource)


    ///infoBox
    private fun getInfoBoxDbRepository(context: Context): InfoBoxDbRepository{
        return InfoBoxDb(context)
    }

    fun getInfoBoxDbUseCase(): InfoBoxDbUseCase =
        InfoBoxDbUseCaseImpl(getInfoBoxDbRepository())

    private val apiInfoBoxCloudSource:InfoBoxCloudRepository by lazy { InfoBoxCloudSource }

    fun getInfoBoxCloudUseCase(): InfoBoxCloudUseCase =
        InfoBoxCloudUseCaseImpl(apiInfoBoxCloudSource)


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

    ////SharedPreferencesNews
    private val sharedPreferencesNews:SharedPreferencesNewsRepository by lazy {
        SharedPreferencesNews(App.instance)
    }

    fun getSharedPreferencesNews():SharedPreferencesNewsUseCase =
        SharedPreferencesNewsUseCaseImpl(sharedPreferencesNews)
}