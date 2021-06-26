package thesis_project.data.data_base.news

import androidx.room.Database
import androidx.room.RoomDatabase
import thesis_project.data.data_base.Rate

@Database(entities = [News::class],version = 1)
abstract class NewsDataBase : RoomDatabase(), GetNewsDao {

}