package thesis_project.data.data_base.atm

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import thesis_project.domain.repository.AtmDbRepository

class AtmDb(context: Context): AtmDbRepository {

    private val dbAtm =
        Room.databaseBuilder(context, AtmDataBase::class.java, "atmDatabase").build()

    private val atmDao = dbAtm.getAtmDao()

    override suspend fun getAtmCountryDb(): Flow<List<AtmData>> {
        val list:Flow<List<AtmData>>
        withContext(Dispatchers.IO){
            list = atmDao.getAtmCountry()
        }
        return list
    }

    override suspend fun getAtmCityDb(city: String): Flow<List<AtmData>> {
        return atmDao.getAtmCity(city)
    }

    override suspend fun insertListAtmDb(atmList: List<AtmData>) {
        withContext(Dispatchers.IO){
            atmDao.insertListAtm(atmList)
        }
    }


}