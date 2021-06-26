package thesis_project.data.data_base.atm

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AtmPojo(
    @SerializedName("city_type")val cityType:String,
    @SerializedName("city")val city :String,
    @SerializedName("address_type")val addressType :String,
    @SerializedName("address")val address :String,
    @SerializedName("house")val house :String,
    @SerializedName("work_time")val workTime :String,
    @SerializedName("gps_x")val latitude :String,
    @SerializedName("gps_y")val longitude :String,
    @SerializedName("ATM_type")val atmType :String,
    @SerializedName("ATM_error")val workingCapacity :String,
    @PrimaryKey
    val id:String,
    val currency:String,
    val cash_in:String
)