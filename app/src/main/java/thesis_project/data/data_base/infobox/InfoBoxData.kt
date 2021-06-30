package thesis_project.data.data_base.infobox

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class InfoBoxData(
    @PrimaryKey
    @SerializedName("info_id") val id:String,
    @SerializedName("city_type") val typeCity:String,
    val city:String,
    @SerializedName("address_type") val typeAddress:String,
    @SerializedName("address") val street:String,
    val house:String,
    @SerializedName("install_place") val place:String,
    @SerializedName("location_name_desc") val description:String,
    @SerializedName("work_time") val workTime:String,
    @SerializedName("gps_x")val latitude :String,
    @SerializedName("gps_y")val longitude :String,
    val currency:String,
    @SerializedName("cash_in_exist") val cashIn:String,
    @SerializedName("inf_status") val workingCondition:String
)
