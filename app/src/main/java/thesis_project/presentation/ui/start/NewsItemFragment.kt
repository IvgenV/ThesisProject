package thesis_project.presentation.ui.start


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thesis_project.R
import com.google.android.material.snackbar.Snackbar
import thesis_project.presentation.viewmodel.ViewModel

class NewsItemFragment : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var webView: WebView
    var snackbar: Snackbar? = null

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

        val html = "<HTML><HEAD>" +
                "<LINK href=\"file:///android_asset/styles.css\" type=\"text/css\" rel=\"stylesheet\"/>" +
                "<script type=\"text/javascript\">" +
                "window.onload = function () {" +
                "var images = document.getElementsByTagName('img');" +
                "for (let i = 0; i < images.length; i++) {" +
                "images[i].onclick = function () {" +
                "PictureToast.showToast();" +
                "};" +
                "}" +
                "};" +
                "</script>" +
                "</HEAD>" +
                "<body><d>$bodyDate</d><br><br>" +
                "<t>$bodyTitle</t>" +
                "<n>$bodyNews</n>" +
                "</body>" +
                "</HTML>"

        loadUrl(html)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun loadUrl(url: String) {

        val webSettings = webView.settings

        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptEnabled = true
        webSettings.defaultFontSize = resources.getDimension(R.dimen.txtWebViewSize).toInt()


        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                if (url.startsWith("mailto")) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("$url ?subject=заголовок &body=текст")

                        childFragmentManager.commit {
                            setReorderingAllowed(true)

                        }
//                        data = Uri.parse(url)
//                        or
//                        data = Uri.parse("mailto:")
//                        putExtra(Intent.EXTRA_EMAIL, url.substringAfter("mailto:"))
//
//                        also can use:
//                        putExtra(Intent.EXTRA_SUBJECT, "subject")
//                        putExtra(Intent.EXTRA_TEXT, "text")
                    }
                    return resolveActivity(intent)
                }

                if (url.startsWith("tel")) {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse(url)
                    }
                    return resolveActivity(intent)
                }

                if (url.startsWith("https://t.me") || url.endsWith(".pdf") || url.startsWith("https://play.")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    return resolveActivity(intent)
                }


                return false
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                val url = request?.url.toString()
                if (url.endsWith("htm") || url.endsWith("html")) {
                    snackbar = Snackbar.make(
                        requireView(),
                        R.string.snackbar_back,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackbar?.setAction(R.string.snackbar_back) {
                        if (webView.canGoBack()) {
                            webView.goBack()
                            snackbar?.dismiss()
                        } else {
                            val back = findNavController().popBackStack()
                            if (!back) {
                                requireActivity().finish()
                            }
                        }
                    }
                    snackbar?.show()
                }
            }

        }


        webView.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun showToast() {
                Toast.makeText(requireContext(), R.string.picture_toast, Toast.LENGTH_SHORT).show()
            }
        }, "PictureToast")
        webView.loadDataWithBaseURL("file:///android_asset/", url, "text/html", "UTF-8", null)


        webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_BACK -> if (webView.canGoBack()) {
                            webView.goBack()
                            snackbar?.dismiss()
                            return true
                        }
                    }
                }
                snackbar?.dismiss()
                return false
            }
        })

    }

    fun resolveActivity(intent: Intent): Boolean {
        return if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
            true
        } else {
            false
        }
    }

}