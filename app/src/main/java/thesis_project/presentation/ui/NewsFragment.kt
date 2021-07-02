package thesis_project.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import thesis_project.data.data_base.news.News
import thesis_project.presentation.viewmodel.ViewModel
import thesis_project.presentation.adapter.NewsAdapter

class NewsFragment: Fragment() {

    lateinit var viewModel: ViewModel
    val adapter = NewsAdapter()
    lateinit var newsList:RecyclerView
    lateinit var switchNotification: Switch
    val key_switch = "APP_SWITCHNEWS"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.getNews().observe(viewLifecycleOwner,{
            adapter.setData(it)
        })
        
        switchNotification.isChecked=viewModel.takeStatusSwitch(key_switch)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsList = view.findViewById(R.id.Newsrecycler)
        newsList.layoutManager = LinearLayoutManager(requireContext())
        newsList.adapter = adapter
        
        switchNotification=view.findViewById(R.id.switch1)
        
        switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked==true && !viewModel.takeStatusSwitch(key_switch)){
                viewModel.startNotificationNews()
            }else if(isChecked==false && viewModel.takeStatusSwitch(key_switch) ){
                viewModel.stopNotificationNews()
            }
            viewModel.addStatusSwitch(key_switch, isChecked)
        }
        
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}