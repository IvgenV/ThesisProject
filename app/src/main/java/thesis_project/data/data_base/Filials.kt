package thesis_project.data.data_base

import com.google.gson.annotations.SerializedName

data class Filials(
    @SerializedName("filial_id") val id:String,
    @SerializedName("GPS_X") val latitude:String,
    @SerializedName("GPS_Y") val longitude:String)