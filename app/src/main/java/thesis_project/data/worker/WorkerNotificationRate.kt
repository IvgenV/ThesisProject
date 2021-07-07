package thesis_project.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.AvailableNetworkInfo
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.thesis_project.R
import thesis_project.Dependencies
import thesis_project.domain.use_case.SharedPreferencesRateDoubleUseCase
import thesis_project.domain.use_case.SharedPreferencesSwitchUseCase
import thesis_project.presentation.ui.start.StartActivity

class WorkerNotificationRate(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private var notificationManager: NotificationManager? = null
    private val NOTIFY_ID = 2
    private val CHANNEL_ID = appContext.getString(R.string.channel_id_Rate)
    private var eur_in: Double = 0.0
    private var usd_in: Double = 0.0
    private var rub_in: Double = 0.0
    private var uah_in: Double = 0.0
    private var rub_out: Double = 0.0
    private var usd_out: Double = 0.0
    private var eur_out: Double = 0.0
    private var uah_out: Double = 0.0


    private var notificationRub: Boolean = false
    private var notificationUsd: Boolean = false
    private var notificationUah: Boolean = false
    private var notificationEur: Boolean = false

    val key_switchRub = appContext.getString(R.string.key_switchRub)
    val key_switchUsd = appContext.getString(R.string.key_switchUsd)
    val key_switchUah = appContext.getString(R.string.key_switchUah)
    val key_switchEur = appContext.getString(R.string.key_switchEur)

    val key_BuyRub = appContext.getString(R.string.key_buyRub)
    val key_BuyUsd = appContext.getString(R.string.key_buyUsd)
    val key_BuyUah = appContext.getString(R.string.key_buyUah)
    val key_BuyEur = appContext.getString(R.string.key_buyEur)
    val key_SaleRub = appContext.getString(R.string.key_saleRub)
    val key_SaleUsd = appContext.getString(R.string.key_saleUsd)
    val key_SaleUah = appContext.getString(R.string.key_saleUah)
    val key_SaleEur = appContext.getString(R.string.key_saleEur)

    val sharedPreferencesSwitch: SharedPreferencesSwitchUseCase by lazy { Dependencies.getSharedPreferenceSwitch() }
    val sharedPreferencesRate: SharedPreferencesRateDoubleUseCase by lazy { Dependencies.getSharedPreferenceRate() }


    override suspend fun doWork(): Result {

        val callRate = Dependencies.getRateCloudUseCase().getRateCountry()

        rub_in = takeRateSharedPreferences(key_BuyRub)
        usd_in = takeRateSharedPreferences(key_BuyUsd)
        eur_in = takeRateSharedPreferences(key_BuyEur)
        uah_in = takeRateSharedPreferences(key_BuyUah)
        rub_out = takeRateSharedPreferences(key_SaleRub)
        usd_out = takeRateSharedPreferences(key_SaleUsd)
        uah_out = takeRateSharedPreferences(key_SaleUah)
        eur_out = takeRateSharedPreferences(key_SaleEur)

        notificationRub = takeStatusSwitch(key_switchRub)
        notificationUsd = takeStatusSwitch(key_switchUsd)
        notificationUah = takeStatusSwitch(key_switchUah)
        notificationEur = takeStatusSwitch(key_switchEur)

        if (callRate.isSuccessful) {
            callRate.body()?.forEach { rate ->
                if (notificationRub == true) {
                    if ((rate.rub_in.toDouble() >= rub_in) || (rate.rub_out.toDouble() <= rub_out)) {
                        showNotification("Изменился Рубль!!!")
                    }
                }
                if (notificationUsd == true) {
                    if ((rate.usd_in.toDouble() >= usd_in) || (rate.usd_out.toDouble() <= usd_out)) {
                        showNotification("Изменился Доллар!!!")
                    }
                }
                if (notificationUah == true) {
                    if ((rate.uah_in.toDouble() >= uah_in) || (rate.uah_out.toDouble() <= uah_out)) {
                        showNotification("Изменился Юань!!!")
                    }
                }
                if (notificationEur == true) {
                    if ((rate.euro_in.toDouble() >= eur_in) || (rate.euro_out.toDouble() <= eur_out)) {
                        showNotification("Изменился Евро!!!")
                    }
                }
            }
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

    fun takeStatusSwitch(key: String): Boolean {
        return sharedPreferencesSwitch.take(key)
    }

    fun takeRateSharedPreferences(key: String): Double {
        return sharedPreferencesRate.take(key)
    }

    fun showNotification(text: String) {
        notificationManager =
            (applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)) as NotificationManager
        val intent = Intent(applicationContext, StartActivity::class.java)
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
                .setContentText(text)
                .setPriority(AvailableNetworkInfo.PRIORITY_HIGH);
        createChannelIfNeeded(notificationManager ?: return)
        (notificationManager ?: return).notify(
            NOTIFY_ID,
            notificationBuilder.build()
        )
    }
}