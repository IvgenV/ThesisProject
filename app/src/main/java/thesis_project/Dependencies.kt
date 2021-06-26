package thesis_project

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thesis_project.data.cloud.atm.AtmCloudSource
import thesis_project.data.cloud.rate.RateCloudSource
import thesis_project.data.data_base.atm.AtmDbData
import thesis_project.data.data_base.filials.СoordinatesPojo
import thesis_project.data.data_base.filials.RateDbData
import thesis_project.domain.entity.AtmCloudUseCaseImpl
import thesis_project.domain.entity.AtmDbUseCaseImpl
import thesis_project.domain.entity.RateCloudUseCaseImpl
import thesis_project.domain.entity.RateDbUseCaseImpl
import thesis_project.domain.repository.AtmCloudRepository
import thesis_project.domain.repository.AtmDbRepository
import thesis_project.domain.repository.RateCloudRepository
import thesis_project.domain.repository.RateDbRepository
import thesis_project.domain.use_case.AtmCloudUseCase
import thesis_project.domain.use_case.AtmDbUseCase
import thesis_project.domain.use_case.RateCloudUseCase
import thesis_project.domain.use_case.RateDbUseCase
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
}