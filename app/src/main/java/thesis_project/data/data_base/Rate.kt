package thesis_project.data.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Rate(@SerializedName("USD_in") val usd: String,
                @SerializedName("EUR_in") val euro_by:String,
                @PrimaryKey
                         @SerializedName("filials_text") val filial:String,
                @SerializedName("home_number") val home:String,
                @SerializedName("street_type") val streetType:String,
                @SerializedName("street") val street:String,
                @SerializedName("name") val name:String) {
    override fun toString(): String {
        return "usd='$usd', euro_by='$euro_by', filial='$filial', home='$home', " +
                "streetType='$streetType', street='$street', city='$name"
    }
}