package thesis_project.data.data_base.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity
@JsonClass(generateAdapter = true)
data class News(
    @PrimaryKey
    @Json(name = "name_ru") val name_ru: String,
    @Json(name = "html_ru") var html_ru: String,
    @Json(name = "img") val img: String,
    @Json(name = "start_date") val start_date: String,
) : Serializable {

    override fun toString(): String {
        return "Tag='$name_ru', text='$html_ru', image='$img', data='$start_date'"
    }
}