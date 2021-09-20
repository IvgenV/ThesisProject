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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import thesis_project.presentation.viewmodel.MyViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.location.GpsLocation
import thesis_project.location.ILocationListener
import thesis_project.presentation.adapter.ItemDistanceAdapter
import thesis_project.presentation.adapter.ToFragmentMap
import thesis_project.sealed.Currency
import thesis_project.sealed.CurrencyOperation

class FragmentFilials : BaseFragment(), ILocationListener, ToFragmentMap {

    lateinit var myViewModel: MyViewModel
    lateinit var filialList: RecyclerView
    val adapter = ItemDistanceAdapter()
    lateinit var navigation: NavController
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    var isGPSEnabled = false
    var isNetworkEnabled = false
    var rate: Double = 0.0
    lateinit var inOut:CurrencyOperation
    lateinit var currency:Currency


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
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        startActivity.setBottomNavigationVisible(false)
        init()
        rate = arguments?.getString("rate")?.toDouble() ?: -1.0
        inOut = CurrencyOperation.fromValue(arguments?.getInt("in_out") ?: -1)
        currency = Currency.fromValue(arguments?.getInt("currency") ?: -1)

        myViewModel.createListFilial(rate, inOut, currency, location)
        myViewModel.getRatFilials().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filialList = view.findViewById(R.id.filials_recycler)
        filialList.layoutManager = LinearLayoutManager(requireContext())
        filialList.adapter = adapter
        navigation = Navigation.findNavController(view)
        adapter.setListenerToMap(this)
    }

    override fun onClick(filial: String) {
        val bundle = Bundle()
        bundle.putString("filial",filial)
        navigation.navigate(R.id.fragment_map, bundle)
    }

    override fun onDestroy() {
        locationManager?.removeUpdates(gpsLocation)
        super.onDestroy()
    }

    override fun onLocationChanged(location: Location) {

    }

    fun init() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false
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
                    1,
                    1F,
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
                    1,
                    1F,
                    gpsLocation
                )
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }
        }


    }

}