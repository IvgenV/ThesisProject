package thesis_project.data.data_base


import com.google.gson.annotations.SerializedName


data class Rate(@SerializedName("USD_in") val usd_in: String,
                @SerializedName("USD_out") val usd_out:String,
                @SerializedName("EUR_in") val euro_in:String,
                @SerializedName("EUR_out") val euro_out:String,
                @SerializedName("RUB_in") val rub_in:String,
                @SerializedName("RUB_out") val rub_out:String,
                @SerializedName("UAH_in") val uah_in:String,
                @SerializedName("UAH_out") val uah_out:String,
                @SerializedName("filial_id") val id:String,
                @SerializedName("filials_text") val filial:String,
                @SerializedName("home_number") val home:String,
                @SerializedName("street_type") val streetType:String,
                @SerializedName("street") val street:String,
                @SerializedName("name") val name:String)