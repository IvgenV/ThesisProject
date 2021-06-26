package thesis_project.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import thesis_project.presentation.viewmodel.ViewModel

class FragmentMap: Fragment(),OnMapReadyCallback {

    lateinit var mapView:MapView
    lateinit var googleMap: GoogleMap
    lateinit var viewModel:ViewModel
    lateinit var lt:LatLng

    var latLng: LatLng = LatLng(53.83965386903869, 27.57576296414777)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment4,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val filial = arguments?.getString("filial")
        filial?.let { viewModel.createGps(it) }
        viewModel.getGps().observe(viewLifecycleOwner,{
            googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("BelarusBank №511/383"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,18f))
        })
    }

}