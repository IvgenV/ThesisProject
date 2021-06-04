package thesis_project.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import thesis_project.presentation.viewmodel.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import thesis_project.presentation.adapter.RateAdapter

class Fragment3: Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var filialList:RecyclerView
    val adapter = RateAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        arguments?.getString("rate",)?.let { viewModel.createRateListFragment3(it) }
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
    }

}