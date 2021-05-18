package thesis_project.data.data_base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import thesis_project.data.data_base.Rate

@Database(entities = [Rate::class],version = 1)
abstract class RateDataBase : RoomDatabase(),GetRateDao{

}