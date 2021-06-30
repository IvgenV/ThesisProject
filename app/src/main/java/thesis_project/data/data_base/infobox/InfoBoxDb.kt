package thesis_project.data.data_base.infobox

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import thesis_project.domain.repository.InfoBoxDbRepository

class InfoBoxDb(context: Context) : InfoBoxDbRepository {

    private val dbInfoBox = Room.databaseBuilder(context,
        InfoBoxDataBase::class.java, "infoBoxDataBase").build()

    private val infoBoxDao = dbInfoBox.getInfoBoxDao()

    override suspend fun insertListInfoBox(infoBoxList: List<InfoBoxData>) {
        withContext(Dispatchers.IO){
            infoBoxDao.insertListInfoBox(infoBoxList)
        }
    }

    override suspend fun getInfoBoxCountry(): List<InfoBoxData> {
        val list:List<InfoBoxData>
        withContext(Dispatchers.IO){
            list = infoBoxDao.getInfoBoxCountry()
        }
        return list
    }

    override suspend fun getInfoBoxCity(city: String): List<InfoBoxData> {
        val list:List<InfoBoxData>
        withContext(Dispatchers.IO){
            list = infoBoxDao.getInfoBoxCity(city)
        }
        return list
    }


}