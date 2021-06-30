package thesis_project.data.data_base.infobox

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InfoBoxData::class], version = 1)
abstract class InfoBoxDataBase:RoomDatabase(),GetInfoBoxDao