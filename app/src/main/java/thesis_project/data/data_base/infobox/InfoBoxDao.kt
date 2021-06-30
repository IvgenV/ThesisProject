package thesis_project.data.data_base.infobox

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InfoBoxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListInfoBox(infoBoxList:List<InfoBoxData>)

    @Query("Select * from InfoBoxData")
    fun getInfoBoxCountry():List<InfoBoxData>

    @Query("Select * from InfoBoxData WHERE city=:city")
    fun getInfoBoxCity(city:String):List<InfoBoxData>

}