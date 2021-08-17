package thesis_project.presentation.ui.start


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import com.squareup.picasso.Picasso
import thesis_project.presentation.viewmodel.ViewModel

class NewsItemFragment : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var webView: WebView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_item, container, false)
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.news_text)

        val bodyNews = arguments?.getString("html_ru").toString()
        val bodyTitle = arguments?.getString("name_ru").toString()
        val bodyDate = arguments?.getString("start_date").toString()

        val html =
            "$bodyDate<br><br><p style=\"color:#009624\">$bodyTitle</p>$bodyNews<img src=${arguments?.getString("img").toString()}>"

        webView.settings.javaScriptEnabled = true
        webView.loadDataWithBaseURL(null, html, null, null, null)

    }

}