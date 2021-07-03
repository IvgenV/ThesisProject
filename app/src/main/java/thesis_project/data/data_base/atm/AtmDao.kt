package thesis_project.data.data_base.atm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AtmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListAtm(atmList:List<AtmData>)

    @Query("Select * from AtmData")
    fun getAtmCountry(): Flow<List<AtmData>>

    @Query("Select * from AtmData WHERE city=:city")
    fun getAtmCity(city:String):Flow<List<AtmData>>

}