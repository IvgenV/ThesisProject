package thesis_project.presentation.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import thesis_project.presentation.viewmodel.ViewModel
import thesis_project.presentation.adapter.RateAdapter

class Fragment2: Fragment() {

    lateinit var viewModel: ViewModel
    val adapter = RateAdapter()
    lateinit var rateList:RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.initial()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment2,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rateList = view.findViewById(R.id.recyclerRate)
        rateList.layoutManager = LinearLayoutManager(requireContext())
        rateList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.dollar_setting){
            ///почему только со второй попытки работает?
            viewModel.updateCountryRate()
            viewModel.getCountryRate().observe(viewLifecycleOwner,{
                ///viewModel.updateCountryRate() сдес не работает(не упевает?)
                adapter.setData(it)
            })
        }
       /*if(id == R.id.euro_setting){
           viewModel.listOfDollar.value = listOf("dsdsd")
           viewModel.updateCountryRate()
       }*/

        return super.onOptionsItemSelected(item)
    }
}