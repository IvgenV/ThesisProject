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

    suspend fun getRateList():List<Rate> {
        var list: List<Rate>
        withContext(Dispatchers.IO){
            list = rateDao.getRate()
        }
        return list
    }

    suspend fun addRateMinsk(rateMinskList: List<Rate>){

        withContext(Dispatchers.IO){
            rateDao.insertListMinsk(rateMinskList)
        }

    }

    suspend fun addRateBrest(rateBrestList: List<Rate>){

        withContext(Dispatchers.IO){
            rateDao.insertListBrest(rateBrestList)
        }

    }

}