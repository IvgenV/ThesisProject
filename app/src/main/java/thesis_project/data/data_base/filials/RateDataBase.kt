package thesis_project.data.data_base.filials

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RateFilialPojo::class],version = 1)
abstract class RateDataBase : RoomDatabase(), GetRateDao