package thesis_project.domain.use_case

import com.google.android.gms.maps.model.LatLng
import thesis_project.data.data_base.Data
import thesis_project.data.data_base.Rate

interface RateDbUseCase {

    suspend fun getRateCountry():List<Data>
    suspend fun getRateCity(city:String):List<Data>
    suspend fun addListRate(rateList: List<Data>)

}