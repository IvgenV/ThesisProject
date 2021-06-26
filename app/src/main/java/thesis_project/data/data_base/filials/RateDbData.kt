package thesis_project.data.data_base.filials

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.domain.repository.RateDbRepository

class RateDbData(context: Context) : RateDbRepository {

    private val db = Room.databaseBuilder(
        context, RateDataBase::class.java,
        "rateDatabase"
    ).build()


    private val rateDao = db.getRateDao()

    override suspend fun getRateCountry(): List<RateFilialPojo> {
        val list: List<RateFilialPojo>
        withContext(Dispatchers.IO) {
            list = rateDao.getRateCountry()
        }
        return list
    }

    override suspend fun getRateCity(city: String): List<RateFilialPojo> {
        val list: List<RateFilialPojo>
        withContext(Dispatchers.IO) {
            list = rateDao.getRateCity(city)
        }
        return list
    }

    override suspend fun addListRate(rateList: List<RateFilialPojo>) {

        withContext(Dispatchers.IO) {
            rateDao.insertListRate(rateList)
        }

    }

}