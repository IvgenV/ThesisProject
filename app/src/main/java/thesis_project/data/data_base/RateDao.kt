package thesis_project.data.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thesis_project.data.data_base.Rate

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(rateList: List<Data>)

    @Query("Select * from Data")
    fun getRateCountry():List<Data>

    @Query("Select * from Data WHERE name=:city")
    fun getRateCity(city:String):List<Data>

}