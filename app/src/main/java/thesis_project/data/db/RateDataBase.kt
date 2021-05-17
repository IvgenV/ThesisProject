package thesis_project.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import thesis_project.Rate

@Database(entities = [Rate::class],version = 1)
abstract class RateDataBase : RoomDatabase(),GetRateDao