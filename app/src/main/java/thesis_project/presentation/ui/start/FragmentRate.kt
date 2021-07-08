package thesis_project.presentation.ui.start

import android.os.Bundle
import android.util.Log
import android.view.*
import thesis_project.Constnsts
import android.widget.*
import android.widget.NumberPicker.OnScrollListener.*
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
import thesis_project.sealed.CurrencyOperation

class FragmentRate : Fragment(), ToFragmentFilials {

    lateinit var viewModel: ViewModel
    val adapter = RateAdapter()
    lateinit var rateList: RecyclerView
    lateinit var rateNP: NumberPicker
    lateinit var switch: SwitchMaterial
    lateinit var navigation: NavController
    lateinit var progressBar: ProgressBar
    val listRate = arrayOf("USD", "EUR", "RUB", "UAH")
    var check: CurrencyOperation = CurrencyOperation.Buy
    var flag = true


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        initialCountryRate()

        viewModel.getProgress().observe(viewLifecycleOwner, {
            progressBar.visibility = it
        })

        rateNP.maxValue = Constnsts.uah
        rateNP.minValue = Constnsts.usd
        rateNP.displayedValues = listRate

        rateNP.setOnValueChangedListener { picker, oldVal, newVal ->
            check = if (switch.isChecked) CurrencyOperation.Sell
            else CurrencyOperation.Buy

                when (newVal) {
                    Constnsts.usd -> {
                        viewModel.createListCurrency(check, newVal)
                        viewModel.getListCurrency().observe(viewLifecycleOwner, {
                            adapter.setData(it)
                        })
                    }
                    Constnsts.eur -> {
                        viewModel.createListCurrency(check, newVal)
                        viewModel.getListCurrency().observe(viewLifecycleOwner, {
                            adapter.setData(it)
                        })
                    }
                    Constnsts.rub -> {
                        viewModel.createListCurrency(check, newVal)
                        viewModel.getListCurrency().observe(viewLifecycleOwner, {
                            adapter.setData(it)
                        })
                    }
                    Constnsts.uah -> {
                        viewModel.createListCurrency(check, newVal)
                        viewModel.getListCurrency().observe(viewLifecycleOwner, {
                            adapter.setData(it)
                        })
                    }
                }
        }
    }

    fun initialCountryRate() {
        viewModel.initialCountryRate()
        viewModel.createListCurrency(CurrencyOperation.Buy, 0)
        viewModel.getListCurrency().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
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
        rateNP = view.findViewById(R.id.rateNumberPicker)
        navigation = Navigation.findNavController(view)
        adapter.setListenerFr2(this)
        switch = view.findViewById(R.id.rateSwitch)
        progressBar = view.findViewById(R.id.progressBarRate)
    }

    override fun onClick(rate: String) {
        val bundle = Bundle()
        bundle.putString("rate", rate)
        bundle.putInt("in_out", check.toValue())
        bundle.putInt("currency", rateNP.value)
        navigation.navigate(R.id.fragment_filials, bundle)
    }
}