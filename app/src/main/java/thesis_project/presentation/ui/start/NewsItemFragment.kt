package thesis_project.presentation.ui.start


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesis_project.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import thesis_project.Dependencies
import thesis_project.data.data_base.news.News
import thesis_project.presentation.adapter.NewsAdapter
import thesis_project.presentation.viewmodel.ViewModel

class NewsItemFragment : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var name_ru: TextView
    lateinit var start_data: TextView
    lateinit var text: TextView
    lateinit var img: ImageView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_item, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name_ru = view.findViewById(R.id.name_ru)
        start_data = view.findViewById(R.id.start_data)
        text = view.findViewById(R.id.news_text)
        img = view.findViewById(R.id.image)

        name_ru.text = arguments?.getString("name_ru").toString()
        start_data.text = arguments?.getString("start_date").toString()
        text.text = arguments?.getString("html_ru").toString()
        Picasso.get().load(arguments?.getString("img").toString()).into(img);

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}