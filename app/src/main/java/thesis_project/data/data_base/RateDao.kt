package thesis_project.data.data_base.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thesis_project.data.data_base.Rate

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListMinsk(rateMinskList: List<Rate>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListBrest(rateBrestList: List<Rate>)

    @Query("Select * from Rate")
    fun getRate():List<Rate>
}