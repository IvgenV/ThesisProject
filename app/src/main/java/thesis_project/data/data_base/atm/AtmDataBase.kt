package thesis_project.data.data_base.atm

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AtmPojo::class],version = 1)
abstract class AtmDataBase : RoomDatabase(), GetAtmDao