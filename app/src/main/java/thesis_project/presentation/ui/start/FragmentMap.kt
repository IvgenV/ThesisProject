package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentMap : BaseFragment(), OnMapReadyCallback {

    lateinit var mapView: MapView
    lateinit var googleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
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
        val atm = arguments?.getString("atm")
        val infoBOx = arguments?.getString("infoBox")


        if (filial != null) {
            myViewModel.createGpsFilial(filial)
            filial.let { myViewModel.createGpsFilial(it) }
            myViewModel.getGps().observe(viewLifecycleOwner) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("FILIAL")


                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 18f))
            }
        }

        if (atm != null) {
            myViewModel.createGpsAtm(atm)
            myViewModel.getGps().observe(viewLifecycleOwner) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("ATM")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 18f))
            }
        }

        if (myViewModel.getInfoBoxInfo() != null) {
            myViewModel.createGpsInfoBOx(myViewModel.getInfoBoxInfo()?:"")
            myViewModel.getGps().observe(viewLifecycleOwner) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("InfoBox")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 18f))
            }
            myViewModel.setInfoBoxInfo(null)
        }
    }

}