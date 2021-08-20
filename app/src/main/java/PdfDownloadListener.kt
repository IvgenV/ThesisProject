import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.DownloadListener

class PdfDownloadListener(context:Context) : DownloadListener {

    val context  = context

    override fun onDownloadStart(
        url: String?,
        userAgent: String?,
        contentDisposition: String?,
        mimetype: String?,
        contentLength: Long
    ) {
        if(url != null){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            if(intent.resolveActivity(context.packageManager)!=null){
                context.startActivity(intent)
            }
        }
    }

}