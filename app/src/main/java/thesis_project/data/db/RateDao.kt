package thesis_project.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import thesis_project.Rate
import thesis_project.RateList
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rate:Rate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(rateList: List<Rate>)

    @Query("Select * from Rate")
    fun getAll():Flow<List<Rate>>
}