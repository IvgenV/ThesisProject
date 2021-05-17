package thesis_project

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Rate(
    @SerializedName("USD_in") val usd: String,
    @SerializedName("EUR_in") val euro_by:String,
    @SerializedName("filials_text") val filial:String,
    @SerializedName("home_number") val home:String,
    @SerializedName("street_type") val street_type:String,
    @SerializedName("street") val street:String
    ) {
    override fun toString(): String {
        return "usd=$usd, euro_by=$euro_by, филиал=$filial, $street_type $street, дом=$home"
    }
}
