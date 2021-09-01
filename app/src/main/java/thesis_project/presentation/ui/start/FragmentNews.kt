package thesis_project.presentation.ui.start

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.number.NumberFormatter.with
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import com.google.android.material.card.MaterialCardView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import thesis_project.ReadNews
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
    private val sp = "NEWS_SHAREDPREFERENCES"
    lateinit var key:String
    var readNews = ReadNews(mutableListOf())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        key = viewModel.key
        viewModel.initialNews()
        viewModel.setNews()
        viewModel.getNews().observe(viewLifecycleOwner, {
            adapter.setData(it)
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

    override fun onClick(news: News) {
        val bundle = Bundle()
        bundle.putString("name_ru", news.name_ru)
        bundle.putString("start_date", news.start_date)
        bundle.putString("html_ru", news.html_ru)
        navigation.navigate(R.id.news_item, bundle)
    }

    override fun share(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun checkSharedPreferences(card: MaterialCardView, title: String) {
        val sp:SharedPreferences = requireActivity().getSharedPreferences(sp,Context.MODE_PRIVATE)
        val builder = GsonBuilder()
        val gson = builder.create()

        val readnews2 = ReadNews(mutableListOf())
        var str = gson.toJson(readnews2)

        readNews = gson.fromJson(sp.getString(key,str),ReadNews::class.java)
        card.isChecked = readNews.news.contains(title)
    }

    override fun addToSharedPreferences(title: String) {
        val sp:SharedPreferences = requireActivity().getSharedPreferences(sp,Context.MODE_PRIVATE)
        val editor = sp.edit()

        val builder = GsonBuilder()
        val gson = builder.create()

        val readnews2 = ReadNews(mutableListOf())
        var str = gson.toJson(readnews2)

        readNews = gson.fromJson(sp.getString(key,str), ReadNews::class.java)
        if(!readNews.news.contains(title)){
            readNews.news.add(title)
        }
        str = gson.toJson(readNews)
        editor.putString(key,str)
        editor.apply()
    }

}