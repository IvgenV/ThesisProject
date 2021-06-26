package thesis_project.data.data_base.filials

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListRate(rateList: List<RateFilialPojo>)

    @Query("Select * from RateFilialPojo")
    fun getRateCountry():List<RateFilialPojo>

    @Query("Select * from RateFilialPojo WHERE name=:city")
    fun getRateCity(city:String):List<RateFilialPojo>

}