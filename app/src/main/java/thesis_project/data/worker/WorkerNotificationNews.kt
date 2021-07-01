package thesis_project.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.AvailableNetworkInfo.PRIORITY_HIGH
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.thesis_project.R
import thesis_project.App
import thesis_project.Dependencies
import thesis_project.data.data_base.news.News
import thesis_project.presentation.ui.MainActivity


class WorkerNotificationNews(appContext: Context,
                             params: WorkerParameters
) :CoroutineWorker(appContext, params) {
    private var notificationManager: NotificationManager? = null
    private val NOTIFY_ID = 1
    private val CHANNEL_ID = "CHANNEL_ID"
    var localNewsDb= Dependencies.getNewsDbUseCase(App.instance)


    override suspend fun doWork(): Result {

        ///проверка на новость
        val listOldNews =localNewsDb.getNewsList()

        val callNews = Dependencies.getNewsCloudUseCase().getNews()
        if (callNews.isSuccessful){
            localNewsDb.addNews(callNews.body()?: listOf())
        }
        val listNewNews=localNewsDb.getNewsList()
        if (listOldNews.get(0).name_ru != listNewNews.get(0).name_ru) {
            ///уведомление
            notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notificationBuilder =
                NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setContentTitle("БеларусьБанк")
                    .setContentText("У нас есть новости для вас!!!!")
                    .setPriority(PRIORITY_HIGH);

            createChannelIfNeeded(notificationManager!!)
            notificationManager!!.notify(NOTIFY_ID, notificationBuilder.build());
        }
        return Result.success()
    }

    //канал для уведомлений
    fun createChannelIfNeeded(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(notificationChannel)
        }
    }
}