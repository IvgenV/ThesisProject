package thesis_project.presentation.ui.start

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
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
    private val adapter = NewsAdapter()
    lateinit var navigation: NavController
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var myFragmentManager: FragmentManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.getNews().observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
        viewModel.getProgress().observe(viewLifecycleOwner, {
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
        /* if (!adapter.hasObservers()) {
             adapter.setHasStableIds(true)
         }*/
        isTablet(newsList)
        navigation = Navigation.findNavController(view)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.greenDark
            )
        )
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getNews()
        }
    }

    override fun onClick(news: News) {
        tabletFragmentNews(news)
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

    fun createFragmentItem() {
        val bundle = Bundle()
        val fragmentItem = NewsItemFragment()
        bundle.putString("name_ru", "")
        bundle.putString("start_date", "")
        bundle.putString("html_ru", "")
        fragmentItem.arguments = bundle
        myFragmentManager.beginTransaction().add(R.id.fragment_news_content, fragmentItem).commit()
    }

    fun tabletFragmentNews(news: News) {
        if (resources.getBoolean(R.bool.isTablet)) {
            val bundle = Bundle()
            val fragmentItem = NewsItemFragment()
            bundle.putString("name_ru", news.name_ru)
            bundle.putString("start_date", news.start_date)
            bundle.putString("html_ru", news.html_ru)
            fragmentItem.arguments = bundle
            viewModel.markNewsAsChecked(news.name_ru)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_news_content, fragmentItem).commit()
        } else {
            val bundle = Bundle()
            bundle.putString("name_ru", news.name_ru)
            bundle.putString("start_date", news.start_date)
            bundle.putString("html_ru", news.html_ru)
            viewModel.markNewsAsChecked(news.name_ru)
            navigation.navigate(R.id.news_item, bundle)
        }
    }

    fun isTablet(newsList: RecyclerView) {
        if (resources.getBoolean(R.bool.isTablet)) {
            newsList.layoutManager = LinearLayoutManager(requireContext())
            newsList.adapter = adapter
            myFragmentManager = requireActivity().supportFragmentManager
            createFragmentItem()
        }
        if (!resources.getBoolean(R.bool.isTablet)) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                newsList.layoutManager = LinearLayoutManager(requireContext())
                newsList.adapter = adapter
            } else {
                newsList.layoutManager = GridLayoutManager(requireContext(), 2)
                newsList.adapter = adapter
            }
        }
    }
}