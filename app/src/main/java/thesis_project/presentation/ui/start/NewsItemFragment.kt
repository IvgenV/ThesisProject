package thesis_project.presentation.ui.start


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.MailTo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
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

        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {


                /// Intent.ACTION_SEND и Intent.VIEW делают одно и тоже сдесь
                /// как правильно и где тогда применять Intent.ACTION_SEND
                ///putExtra имейл не забирает
                if (url.startsWith("mailto")) {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse(url)
                        putExtra(Intent.EXTRA_SUBJECT,"мой заголовок")
                        putExtra(Intent.EXTRA_TEXT,"мой текст")
                    }
                    return resolveActivity(intent)
                }

                if (url.startsWith("tel")) {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse(url)
                    }
                    return resolveActivity(intent)
                }

                if(url.startsWith("https://t.me")||url.endsWith(".pdf")||url.startsWith("https://play.")){
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

                Log.d("onReceivedError","$request   ${error?.description}")

                snackbar = Snackbar.make(
                    requireView(),
                    R.string.Snackbar_Notification,
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar?.setAction(R.string.Snackbar_back) {
                    if (webView.canGoBack()) {
                        webView.goBack()
                        snackbar?.dismiss()
                    }else {
                        val back = findNavController().popBackStack()
                        if(!back){
                            requireActivity().finish()
                        }
                    }
                }
                snackbar?.show()
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
                return false
            }
        })

    }

    fun resolveActivity(intent:Intent):Boolean{
        return if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
            true
        } else {
            false
        }
    }

}