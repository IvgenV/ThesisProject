package thesis_project.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import thesis_project.presentation.viewmodel.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.presentation.adapter.RateAdapter
import thesis_project.presentation.adapter.ToFragment4

class Fragment3: Fragment(),ToFragment4 {

    lateinit var viewModel: ViewModel
    lateinit var filialList:RecyclerView
    val adapter = RateAdapter()
    lateinit var navigation:NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        arguments?.getString("rate")?.let { viewModel.createRateListFragment3(it) }
        viewModel.getFilials().observe(viewLifecycleOwner,{
            adapter.setData(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment3,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filialList = view.findViewById(R.id.bank_branch)
        filialList.layoutManager = LinearLayoutManager(requireContext())
        filialList.adapter = adapter
        navigation = Navigation.findNavController(view)
        adapter.setListenerFR3(this)
    }

    override fun onClick(filial: String) {
        val bundle = Bundle()
        viewModel.createGps(filial)
        navigation.navigate(R.id.fragment4,bundle)
    }

}