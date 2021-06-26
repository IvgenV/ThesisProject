package thesis_project.data.data_base.atm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AtmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListAtm(atmList:List<AtmPojo>)

    @Query("Select * from AtmPojo")
    fun getAtmCountry():List<AtmPojo>

    @Query("Select * from AtmPojo WHERE city=:city")
    fun getAtmCity(city:String):List<AtmPojo>

}