package thesis_project.presentation.ui.start

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import thesis_project.StateNews
import thesis_project.data.data_base.news.News
import thesis_project.presentation.adapter.NewsAdapter
import thesis_project.presentation.adapter.ToFragmentNews
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentNews : Fragment(), ToFragmentNews {

    lateinit var myViewModel: MyViewModel
    private val adapter = NewsAdapter()
    lateinit var navigation: NavController
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        myViewModel.getNews().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        myViewModel.getProgress().observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = it == View.VISIBLE
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setListener(this)
        val newsList: RecyclerView = view.findViewById(R.id.rvNews)
        newsList.adapter = adapter
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }
        checkDeviceType(newsList)
        navigation = Navigation.findNavController(view)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.greenDark
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
            myViewModel.getNews()
        }
    }

    override fun onClick(news: News) {
        openFragmentNews(news)
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

    private fun openFragmentNews(news: News) {
        val stateNews = StateNews(news.name_ru, news.start_date, news.html_ru)
        myViewModel.setStateNews(stateNews)
        myViewModel.markNewsAsChecked(news.name_ru)
        if (resources.getBoolean(R.bool.isTablet)) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_news_content, NewsItemFragment()).commit()
        } else {
            navigation.navigate(R.id.news_item)
        }
    }

    fun createFragmentItem() {
        val fragmentItem = NewsItemFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_news_content, fragmentItem).commit()
    }

    private fun checkDeviceType(newsList: RecyclerView) {
        if (resources.getBoolean(R.bool.isTablet)) {
            newsList.layoutManager = LinearLayoutManager(requireContext())
            createFragmentItem()
        } else {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                newsList.layoutManager = LinearLayoutManager(requireContext())
            } else {
                newsList.layoutManager = GridLayoutManager(requireContext(), 2)
            }
        }
    }
}