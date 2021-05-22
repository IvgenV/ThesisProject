package thesis_project.data.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thesis_project.data.data_base.Rate

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(rateList: List<Rate>)

    @Query("Select * from Rate")
    fun getRate():List<Rate>

    @Query("Select * from Rate WHERE name=:city")
    fun getRateCity(city:String):List<Rate>

}