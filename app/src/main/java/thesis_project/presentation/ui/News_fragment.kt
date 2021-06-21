package thesis_project.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesis_project.R
import thesis_project.data.data_base.Rate
import thesis_project.data.data_base.news.News
import thesis_project.presentation.viewmodel.ViewModel
import thesis_project.presentation.adapter.RateAdapter
import thesis_project.presentation.adapter.news.NewsAdapter

class News_fragment: Fragment() {

    lateinit var viewModel: ViewModel
    val adapter = NewsAdapter()
    lateinit var newsList:RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.getNews().observe(viewLifecycleOwner,{
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

        newsList = view.findViewById(R.id.Newsrecycler)
        newsList.layoutManager = LinearLayoutManager(requireContext())
        newsList.adapter = adapter

        /*
        val testNewsList :List<News> = listOf(
            News("First","Добрый день уважаемый пользователь","https:\\/\\/belarusbank.by\\/site_ru\\/37865\\/intro.jpg","2021-06-15"),
            News("Second","Сегодня акция","https:\\/\\/belarusbank.by\\/site_ru\\/37860\\/image001_2.jpg","2021-06-14"),
            News("Therrd","Вчера ты сдал экзамины","https:\\/\\/belarusbank.by\\/site_ru\\/37851\\/intro.jpg","2021-06-10")
        )
        adapter.setData(testNewsList)
        */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}