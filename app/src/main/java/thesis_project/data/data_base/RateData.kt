package thesis_project.data.data_base.db

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.data.data_base.Rate

class RateData(context:Context) {

    private val db = Room.databaseBuilder(context,RateDataBase::class.java,
        "rateDatabase").build()


    private val rateDao = db.getRateDao()

    suspend fun getMinskRate():List<Rate> {
        var list: List<Rate>
        withContext(Dispatchers.IO){
            list = rateDao.getMinskRate()
        }
        return list
    }

    suspend fun addRateList(rateMinskList: List<Rate>){

        withContext(Dispatchers.IO){
            rateDao.insertListMinsk(rateMinskList)
        }

    }

}