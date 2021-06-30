package thesis_project.data.data_base.filials

import com.google.gson.annotations.SerializedName

data class СoordinatesData(
    @SerializedName("filial_id") val id:String,
    @SerializedName("GPS_X") val latitude:String,
    @SerializedName("GPS_Y") val longitude:String)