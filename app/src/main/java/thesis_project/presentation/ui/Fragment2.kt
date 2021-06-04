package thesis_project.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
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
import thesis_project.presentation.adapter.ItemClickListener
import thesis_project.presentation.viewmodel.ViewModel
import thesis_project.presentation.adapter.RateAdapter

class Fragment2 : Fragment(), ILocationListener, ItemClickListener {

    lateinit var viewModel: ViewModel
    val adapter = RateAdapter()
    lateinit var rateList: RecyclerView
    lateinit var rateInput: EditText
    lateinit var locatioRate: TextView
    lateinit var rateIn: NumberPicker
    lateinit var rateOut: NumberPicker
    lateinit var navigation: NavController
    var locationTxt = ""
    val listRate = arrayOf("USD", "RUB", "EUR", "BUR")
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private lateinit var gpsLocation: GpsLocation
    var isGPSEnabled = false
    var isNetworkEnabled = false
    var latitude: Double = 0.0
    var longitude: Double = 0.0
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

        rateIn.maxValue = 3
        rateIn.minValue = 0
        rateOut.maxValue = 3
        rateOut.minValue = 0
        rateIn.displayedValues = listRate
        rateOut.displayedValues = listRate

        viewModel.initialCountryRate()
        locationTxt = "Belarus"
        locatioRate.text = locationTxt
        viewModel.updateDollarListRate(locationTxt)
        viewModel.getCountryRateDollar().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        rateInput.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (rateInput.text?.isEmpty() == true) {
                    viewModel.initialCountryRate()
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.updateDollarListRate(locationTxt)
                    viewModel.getCountryRateDollar().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })

                } else {
                    viewModel.initialCityRate(rateInput.text.toString())
                    locationTxt = rateInput.text.toString()
                    locatioRate.text = locationTxt
                    viewModel.updateDollarListRate(locationTxt)
                    viewModel.getCountryRateDollar().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })
                }
            }
            true
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateList = view.findViewById(R.id.recyclerRate)
        rateList.layoutManager = LinearLayoutManager(requireContext())
        rateList.adapter = adapter
        rateInput = view.findViewById(R.id.cityRateInput)
        locatioRate = view.findViewById(R.id.locationRate)
        rateIn = view.findViewById(R.id.rateNumberPickerIn)
        rateOut = view.findViewById(R.id.rateNumberPickerOut)
        navigation = Navigation.findNavController(view)
        adapter.setListener(this)
    }

    override fun onLocationChanged(location: Location) {

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
                    1,
                    1F,
                    gpsLocation
                )
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }

                if (location != null) {
                    latitude = location?.latitude ?: -999.0
                    longitude = location?.longitude ?: -999.0
                    Log.d("TAaAAAG", "$latitude $longitude")
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
                if (location != null) {
                    latitude = location?.latitude ?: -999.0
                    longitude = location?.latitude ?: -999.0
                    Log.d("TAaAAAG", "$latitude $longitude")
                }
            }

        } else {
            Toast.makeText(requireContext(), "Couldnt find Network!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        locationManager?.removeUpdates(gpsLocation)
        super.onDestroy()
    }

    override fun onClick(rate: String) {
        val bundle = Bundle()
        bundle.putString("rate", rate)
        navigation.navigate(R.id.fragment3, bundle)
    }
}