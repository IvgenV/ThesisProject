package thesis_project.data.data_base.atm

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.domain.repository.AtmDbRepository

class AtmDbData(context: Context): AtmDbRepository {

    private val dbAtm =
        Room.databaseBuilder(context, AtmDataBase::class.java, "atmDatabase").build()

    private val atmDao = dbAtm.getAtmDao()

    override suspend fun getAtmCountry(): List<AtmPojo> {
        val list:List<AtmPojo>
        withContext(Dispatchers.IO){
            list = atmDao.getAtmCountry()
        }
        return list
    }

    override suspend fun getAtmCity(city: String): List<AtmPojo> {
        val list:List<AtmPojo>
        withContext(Dispatchers.IO){
            list = atmDao.getAtmCity(city)
        }
        return list
    }

    override suspend fun addListAtm(atmList: List<AtmPojo>) {
        withContext(Dispatchers.IO){
            atmDao.insertListAtm(atmList)
        }
    }


}