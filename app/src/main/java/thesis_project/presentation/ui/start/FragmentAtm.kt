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
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import kotlinx.coroutines.flow.collect
import thesis_project.location.GpsLocation
import thesis_project.location.ILocationListener
import thesis_project.presentation.adapter.ItemAddressDistanceAdapter
import thesis_project.presentation.adapter.ToFragmentMap
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentAtm : BaseFragment(), ILocationListener, ToFragmentMap {

    lateinit var myViewModel: MyViewModel
    lateinit var atmList: RecyclerView
    val adapter = ItemAddressDistanceAdapter()
    lateinit var navigation: NavController
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    lateinit var buttonRefresh: Button
    lateinit var progressAtm: ProgressBar
    lateinit var tvText: TextView
    var isGPSEnabled = false
    var isNetworkEnabled = false

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (!it.containsValue(false)) {
                initialization()
                lifecycleScope.launchWhenStarted {
                    myViewModel.getAtm().collect {
                        adapter.setData(it)
                    }
                }
            }
        }


    override fun onStart() {
        super.onStart()
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        myViewModel.initialAtmDb()
        startActivity.setBottomNavigationVisible(true)
        createLocationManager()

        myViewModel.getProgress().observe(viewLifecycleOwner, {
            progressAtm.visibility = it
        })

        myViewModel.getCheckLocation().observe(viewLifecycleOwner, {
            if (it == true) {
                tvText.text = "Current data!"
            }
            if (it == false) {
                tvText.text = "Not current data!"
            }
        })

        lifecycleScope.launchWhenStarted {
            myViewModel.getAtm().collect {
                adapter.setData(it)
            }
        }


        buttonRefresh.setOnClickListener {
            initialization()
        }

    }


    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
         viewModel.initialAtmDb()
         createLocationManager()


         viewModel.getProgress().observe(viewLifecycleOwner, {
             progressAtm.visibility = it
         })

         viewModel.getCheckLocation().observe(viewLifecycleOwner, {
             if (it == true) {
                 tvText.text = "Current data!"
             }
             if (it == false) {
                 tvText.text = "Not current data!"
             }
         })

         lifecycleScope.launchWhenStarted {
             viewModel.getAtm().collect {
                 adapter.setData(it)
             }
         }


         buttonRefresh.setOnClickListener {
             initialization()
         }


     }*/

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
        tvText = view.findViewById(R.id.tvText_framgnet_atm)
        progressAtm = view.findViewById(R.id.progressBarAtm)
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
        myViewModel.initialAtmCloud(location)
    }

    override fun onClick(atm: String) {
        val bundle = Bundle()
        bundle.putString("atm", atm)
        navigation.navigate(R.id.fragment_map, bundle)
    }

    fun createLocationManager() {
        if (activity != null && isAdded) {
            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
                gpsLocation = GpsLocation()
                gpsLocation.setLocalListenerInterface(this)

                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    20F,
                    gpsLocation
                )
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    20000,
                    20F,
                    gpsLocation
                )
            }
        }
    }

    fun initialization() {
        initLocation()
        if (location != null) {
            myViewModel.initialAtmCloud(location)
        } else {
            Toast.makeText(requireContext(), "Cant find GPS!", Toast.LENGTH_SHORT).show()
            tvText.text = "Not current data"
        }
    }

    fun initLocation() {
        isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        isNetworkEnabled =
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
        getLocation()
    }


    fun getLocation() {
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
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        return
                    }
                }
            }
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
                if (locationManager != null) {
                    location =
                        locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }
        }

    }
}