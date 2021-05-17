package thesis_project.data.db

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import thesis_project.Rate
import thesis_project.RateList

class RateData(context:Context) {

    private val db = Room.databaseBuilder(context,RateDataBase::class.java,
        "rateDatabase").build()


    private val rateDao = db.getRateDao()

    fun getDollarDb(): Flow<List<Rate>> {
        return rateDao.getAll()
    }

    fun addRateList(rateList: List<Rate>){

            rateDao.insertList(rateList)

    }

}