package thesis_project.web_view

import android.content.Context
import android.widget.Toast

import android.webkit.JavascriptInterface

class JavaScriptInterface internal constructor(c: Context) {
    var mContext: Context = c

    @JavascriptInterface
    fun showToast() {
        Toast.makeText(mContext, "Picture toast!", Toast.LENGTH_SHORT).show()
    }

}