package thesis_project.data.data_base.news

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [News::class],version = 1)
abstract class NewsDataBase : RoomDatabase(), GetNewsDao {

}