package thesis_project.data.data_base.filials

import androidx.room.*

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListRate(rateList: List<RateFilialData>)

    @Query("Select * from RateFilialData")
    fun getRateCountry():List<RateFilialData>

    @Query("Select * from RateFilialData WHERE name=:city")
    fun getRateCity(city:String):List<RateFilialData>

    @Delete
    fun deleteRate(rateList:List<RateFilialData>)

}