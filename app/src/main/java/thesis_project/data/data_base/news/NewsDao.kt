package thesis_project.data.data_base.news

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListNews(newsList: List<News>)

    @Query("Select * from News")
    fun getNews():List<News>
}