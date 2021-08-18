package thesis_project.web_view

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient: WebViewClient() {

    val TAG = "MyWebViewClient_log"

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        Log.d(TAG,"onPageStarted + $url")
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Log.d(TAG,"onPageFinished + $url")
        super.onPageFinished(view, url)
    }

}