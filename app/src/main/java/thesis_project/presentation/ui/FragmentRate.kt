package thesis_project.presentation.ui

import android.os.Bundle
import android.view.*
import thesis_project.Constnsts
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import com.google.android.material.switchmaterial.SwitchMaterial
import thesis_project.presentation.adapter.RateAdapter
import thesis_project.presentation.adapter.ToFragmentFilials
import thesis_project.presentation.viewmodel.ViewModel

class FragmentRate : Fragment(), ToFragmentFilials {

    lateinit var viewModel: ViewModel
    val adapter = RateAdapter()
    lateinit var rateList: RecyclerView
    lateinit var rateInput: EditText
    lateinit var locatioRate: TextView
    lateinit var rateNP: NumberPicker
    lateinit var switch: SwitchMaterial
    lateinit var navigation: NavController
    var locationTxt = ""
    val listRate = arrayOf("USD", "EUR", "RUB", "UAH")
    var check = 0


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)


        rateNP.maxValue = Constnsts.uah
        rateNP.minValue = Constnsts.usd
        rateNP.displayedValues = listRate
        viewModel.initialCountryRate()
        locationTxt = "Belarus"
        locatioRate.text = locationTxt
        viewModel.createListCurrency(locationTxt, 0, 0)
        viewModel.getListCurrency().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        rateNP.setOnValueChangedListener { picker, oldVal, newVal ->
            if(switch.isChecked){
                check = 1
            }
            if(!switch.isChecked){
                check = 0
            }
            when (newVal) {
                Constnsts.usd -> {
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.createListCurrency(locationTxt, check, newVal)
                    viewModel.getListCurrency().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })
                }
                Constnsts.eur -> {
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.createListCurrency(locationTxt, check, newVal)
                    viewModel.getListCurrency().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })
                }
                Constnsts.rub -> {
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.createListCurrency(locationTxt, check, newVal)
                    viewModel.getListCurrency().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })
                }
                Constnsts.uah -> {
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.createListCurrency(locationTxt, check, newVal)
                    viewModel.getListCurrency().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })
                }
            }
        }

        rateInput.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (rateInput.text?.isEmpty() == true) {
                    locationTxt = "Belarus"
                    locatioRate.text = locationTxt
                    viewModel.createListCurrency(locationTxt, check, rateNP.value)
                    viewModel.getListCurrency().observe(viewLifecycleOwner, {
                        adapter.setData(it)
                    })

                } else {
                     locationTxt = rateInput.text.toString()
                     locatioRate.text = locationTxt
                     viewModel.createListCurrency(locationTxt,check,rateNP.value)
                     viewModel.getListCurrency().observe(viewLifecycleOwner, {
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
        return inflater.inflate(R.layout.fragment_rate, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateList = view.findViewById(R.id.recyclerRate)
        rateList.layoutManager = LinearLayoutManager(requireContext())
        rateList.adapter = adapter
        rateInput = view.findViewById(R.id.cityRateInput)
        locatioRate = view.findViewById(R.id.locationRate)
        rateNP = view.findViewById(R.id.rateNumberPicker)
        navigation = Navigation.findNavController(view)
        adapter.setListenerFr2(this)
        switch = view.findViewById(R.id.rateSwitch)
    }

    override fun onClick(rate: String) {
        val bundle = Bundle()
        bundle.putString("rate", rate)
        bundle.putInt("in_out",check)
        bundle.putInt("currency",rateNP.value)
        navigation.navigate(R.id.fragment_filials, bundle)
    }
}