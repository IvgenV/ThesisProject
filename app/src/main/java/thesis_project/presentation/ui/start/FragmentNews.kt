package thesis_project.presentation.ui.start

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import thesis_project.data.data_base.news.News
import thesis_project.presentation.adapter.NewsAdapter
import thesis_project.presentation.adapter.ToFragmentNews
import thesis_project.presentation.viewmodel.ViewModel

class FragmentNews : Fragment(), ToFragmentNews {

    lateinit var viewModel: ViewModel
    val adapter = NewsAdapter()
    lateinit var progressNews: ProgressBar
    lateinit var newsList: RecyclerView
    lateinit var navigation: NavController
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.initialNews()
        viewModel.setNews()
        viewModel.getNews().observe(viewLifecycleOwner, {
            adapter.setData(it)
            progressNews.visibility = View.INVISIBLE
        })

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
        adapter.setListener(this)
        navigation = Navigation.findNavController(view)
        progressNews = view.findViewById(R.id.progressNews)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        //  swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.greenDark))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.initialNews()
            viewModel.setNews()
            swipeRefreshLayout.isRefreshing = false
        }
        newsList = view.findViewById(R.id.Newsrecycler)
        newsList.layoutManager = LinearLayoutManager(requireContext())
        newsList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(news: News) {
        val bundle = Bundle()
        bundle.putString("name_ru", news.name_ru)
        bundle.putString("start_date", news.start_date)
        bundle.putString("html_ru", news.html_ru)
        bundle.putString("img", news.img)
        navigation.navigate(R.id.news_item, bundle)
    }

}