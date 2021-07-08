package thesis_project.presentation.ui.start

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import kotlinx.coroutines.flow.collect
import thesis_project.location.GpsLocation
import thesis_project.location.ILocationListener
import thesis_project.presentation.adapter.ItemDistanceAdapter
import thesis_project.presentation.adapter.ToFragmentMap
import thesis_project.presentation.viewmodel.ViewModel

class FragmentAtm : Fragment(), ILocationListener, ToFragmentMap {

    lateinit var viewModel: ViewModel
    lateinit var atmList: RecyclerView
    val adapter = ItemDistanceAdapter()
    lateinit var navigation: NavController
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    lateinit var buttonRefresh: Button
    var isGPSEnabled = false
    var isNetworkEnabled = false
    var bol = false
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
        viewModel.initialAtm(this.requireContext())
        viewModel.createListAtm(location)

        buttonRefresh.setOnClickListener {

        }

        lifecycleScope.launchWhenStarted {
            viewModel.getAtm().collect {
                adapter.setData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_atm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        atmList = view.findViewById(R.id.atm_recycler)
        atmList.layoutManager = LinearLayoutManager(requireContext())
        atmList.adapter = adapter
        buttonRefresh = view.findViewById(R.id.buttonRefreshAtm)
        navigation = Navigation.findNavController(view)
        adapter.setListenerToMap(this)
    }

    override fun onDestroy() {
        locationManager?.removeUpdates(gpsLocation)
        super.onDestroy()
    }

    override fun onLocationChanged(location: Location) {
        /*viewModel.initialAtm()
        viewModel.createListAtm(location)*/
    }

    override fun onClick(atm: String) {
        val bundle = Bundle()
        bundle.putString("atm", atm)
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
                    100,
                    10F,
                    gpsLocation
                )

                if (locationManager != null) {
                    location =
                        locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }
        }


    }

}