package thesis_project.presentation.ui.start

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
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentInfoBox : BaseFragment(), ILocationListener, ToFragmentMap {

    lateinit var infoBoxList: RecyclerView
    val adapter = ItemDistanceAdapter()
    lateinit var tvText: TextView
    lateinit var buttonRefresh: Button
    lateinit var progressInfoBox: ProgressBar
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    var isGPSEnabled = false
    var isNetworkEnabled = false
    override val bottomNavigationVisible: Boolean
        get() = true

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (!it.containsValue(false)) {
                initialization()
                myViewModel.getInfoBox().observe(viewLifecycleOwner) {
                    adapter.setData(it)
                }
            }
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
        tvText = view.findViewById(R.id.tvTextInfoBox)
        buttonRefresh = view.findViewById(R.id.buttonRefreshInfoBox)
        progressInfoBox = view.findViewById(R.id.progressInfoBox)
        infoBoxList.layoutManager = LinearLayoutManager(requireContext())
        infoBoxList.adapter = adapter
        adapter.setListenerToMap(this)
        createLocationManager()
        initialization()

        myViewModel.getProgress().observe(viewLifecycleOwner) {
            progressInfoBox.visibility = it
        }

        myViewModel.getInfoBox().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }


        buttonRefresh.setOnClickListener {
            initialization()
        }
    }

    override fun onDestroy() {
        locationManager?.removeUpdates(gpsLocation)
        super.onDestroy()
    }


    override fun onLocationChanged(location: Location) {
        /*initialization()*/
    }

    override fun onClick(infoBox: String) {
        /*val bundle = Bundle()
        bundle.putString("infoBox", infoBox)*/
        myViewModel.setInfoBoxInfo(infoBox)
        navigation.navigate(R.id.fragment_map)
    }

    fun initialization() {
        initLocation()
        if (isGPSEnabled) {
            Log.d("location!",location.toString())
            myViewModel.initialInfoBox(location)
            tvText.text = "Current data"

        } else {
            Toast.makeText(requireContext(), "Turn on GPS!", Toast.LENGTH_SHORT).show()
            tvText.text = "Not current data"
        }
    }

    fun createLocationManager() {
        if (activity != null && isAdded) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            gpsLocation = GpsLocation()
            gpsLocation.setLocalListenerInterface(this)
        }
    }

    fun initLocation() {
        isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        isNetworkEnabled =
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
        checkPermission()
    }

    fun checkPermission() {

        if (isGPSEnabled && activity != null && isAdded) {
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
                    1,
                    1F,
                    gpsLocation
                )
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    return
                }
            }
        } else {
            location = null
            return
        }

        if (isNetworkEnabled && activity != null && isAdded) {
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