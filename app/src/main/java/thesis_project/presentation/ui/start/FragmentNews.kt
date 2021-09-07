package thesis_project.presentation.ui.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
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
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.getNews().observe(viewLifecycleOwner, {
            viewModel.initialNews()
            adapter.setData(it)
        })
        viewModel.getProgress().observe(viewLifecycleOwner,{
            progressNews.visibility = it
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
        if(!adapter.hasObservers()){
            adapter.setHasStableIds(true)
        }
        navigation = Navigation.findNavController(view)
        progressNews = view.findViewById(R.id.progressNews)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(),R.color.greenDark))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.initialNews()
            swipeRefreshLayout.isRefreshing = false
        }
        newsList = view.findViewById(R.id.Newsrecycler)
        newsList.layoutManager = LinearLayoutManager(requireContext())
        newsList.adapter = adapter
    }

    override fun onClick(news: News) {
        val bundle = Bundle()
        bundle.putString("name_ru", news.name_ru)
        bundle.putString("start_date", news.start_date)
        bundle.putString("html_ru", news.html_ru)
        viewModel.addNewsSharedPreferences(news.name_ru)
        navigation.navigate(R.id.news_item, bundle)
    }

    override fun share(news: News) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, news.name_ru)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}