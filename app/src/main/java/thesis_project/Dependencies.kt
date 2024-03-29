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
import thesis_project.data.sharedPreferences.SharedPreferencesTheme
import thesis_project.data.worker.WorkerControllerUseCaseImpl
import thesis_project.domain.entity.*
import thesis_project.domain.repository.*
import thesis_project.domain.use_case.*

object Dependencies {

    ////////rate
    private fun getRateDbRepository(context: Context): RateDbRepository {
        return RateDb(context)
    }

    fun getRateDbUseCase(context: Context): RateDbUseCase =
        RateDbUseCaseImpl(getRateDbRepository(context))

    private val apiRateCloudSource: RateCloudRepository by lazy { RateCloudSource() }

    fun getRateCloudUseCase(): RateCloudUseCase =
        RateCloudUseCaseImpl(apiRateCloudSource)


    /////atm
    private fun getAtmDbRepository(context: Context): AtmDbRepository {
        return AtmDb(context)
    }

    private val apiAtmCloudSource: AtmCloudRepository by lazy { AtmCloudSource(Dispatchers.IO) }

    fun getAtmUseCase(context: Context): AtmUseCase =
        AtmUseCaseImpl(getAtmDbRepository(context), apiAtmCloudSource)


    ///infoBox
    private fun getInfoBoxDbRepository(context: Context): InfoBoxDbRepository {
        return InfoBoxDb(context)
    }

    fun getInfoBoxDbUseCase(context: Context): InfoBoxDbUseCase =
        InfoBoxDbUseCaseImpl(getInfoBoxDbRepository(context))

    private val apiInfoBoxCloudSource: InfoBoxCloudRepository by lazy { InfoBoxCloudSource }

    fun getInfoBoxCloudUseCase(): InfoBoxCloudUseCase =
        InfoBoxCloudUseCaseImpl(apiInfoBoxCloudSource)


    ///News
    private val apiNewsCloudSource: NewsCloudRepository by lazy { NewsCloudSource }

    fun getNewsCloudUseCase(): NewsCloudUseCase =
        NewsCloudUseCaseImpl(apiNewsCloudSource)

    private fun getNewsDbRepository(): NewsDbRepository {
        return NewsData(MainApp.instance)
    }

    fun getNewsDbUseCase(): NewsDbUseCase =
        NewsDbUseCaseImpl(getNewsDbRepository())

    //SharedPrefenceSwitch
    private val sharedPreferencesSwitch: SharedPreferencesSwitchRepository by lazy {
        SharedPreferencesSwitch(
            MainApp.instance
        )
    }

    fun getSharedPreferenceSwitch(): SharedPreferencesSwitchUseCase =
        SharedPreferencesSwitchUseCaseImpl(
            sharedPreferencesSwitch
        )

    //SharedPrefence
    private val sharedPreferencesRateDouble: SharedPreferencesRateDoubleRepository by lazy {
        SharedPreferencesRateDouble(
            MainApp.instance
        )
    }

    fun getSharedPreferenceRate(): SharedPreferencesRateDoubleUseCase =
        SharedPreferencesRateDoubleUseCaseImpl(
            sharedPreferencesRateDouble
        )

    ///WorkerNotification
    private val workerController: WorkerControllerUseCase by lazy {
        WorkerControllerUseCaseImpl(
            MainApp.instance
        )
    }

    fun getMyWorkerController(): WorkerControllerUseCase = workerController

    ////SharedPreferencesNews
    private val sharedPreferencesNews: SharedPreferencesNewsRepository by lazy {
        SharedPreferencesNews(MainApp.instance)
    }

    fun getSharedPreferencesNews(): SharedPreferencesNewsUseCase =
        SharedPreferencesNewsUseCaseImpl(sharedPreferencesNews)

    ////SharedPreferencesTheme
    private val sharedPreferencesTheme: SharedPreferencesThemeRepository by lazy {
        SharedPreferencesTheme(MainApp.instance)
    }

    fun getSharedPreferencesTheme(): SharedPreferencesThemeUseCase =
        SharedPreferencesThemeUseCaseImpl(sharedPreferencesTheme)
}