package thesis_project.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.location.GpsLocation
import thesis_project.location.ILocationListener
import thesis_project.presentation.adapter.ItemDistanceAdapter
import thesis_project.presentation.adapter.ToFragmentMap
import thesis_project.presentation.viewmodel.ViewModel

class FragmentInfoBox : Fragment(), ILocationListener, ToFragmentMap {

    lateinit var viewModel: ViewModel
    lateinit var infoBoxList: RecyclerView
    val adapter = ItemDistanceAdapter()
    lateinit var navigation: NavController
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    var isGPSEnabled = false
    var isNetworkEnabled = false
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var bol = false
            it.entries.forEach {
                bol = it.value
            }
            if (bol) {
                checkPermission()
            } else Toast.makeText(requireContext(), "NEED PERMISSION!", Toast.LENGTH_SHORT).show()
        }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        init()

        viewModel.initialInfoBox()
        viewModel.createListInfoBox(location)
        viewModel.getInfoBox().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoBoxList = view.findViewById(R.id.infoBox_recycler)
        infoBoxList.layoutManager = LinearLayoutManager(requireContext())
        infoBoxList.adapter = adapter
        navigation = Navigation.findNavController(view)
        adapter.setListenerToMap(this)
    }

    override fun onDestroy() {
        locationManager?.removeUpdates(gpsLocation)
        super.onDestroy()
    }


    override fun onLocationChanged(location: Location) {

    }

    override fun onClick(infoBox: String) {
        val bundle = Bundle()
        bundle.putString("infoBox", infoBox)
        navigation.navigate(R.id.fragment_map, bundle)
    }

    fun init() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        isNetworkEnabled =
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
        gpsLocation = GpsLocation()
        gpsLocation.setLocalListenerInterface(this)
        checkPermission()
    }

    fun checkPermission() {
        if (isGPSEnabled) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    100,
                    10F,
                    gpsLocation
                )
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Couldnt find gps!", Toast.LENGTH_SHORT).show()
        }

        if (isNetworkEnabled) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestMultiplePermissions.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            } else {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1,
                    1f,
                    gpsLocation
                )
                if (locationManager != null) {
                    location =
                        locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }

        } else {
            Toast.makeText(requireContext(), "Couldnt find Network!", Toast.LENGTH_SHORT).show()
        }
    }
}