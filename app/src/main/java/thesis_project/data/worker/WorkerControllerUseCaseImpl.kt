package thesis_project.data.worker


import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.thesis_project.R
import thesis_project.domain.use_case.WorkerControllerUseCase


class WorkerControllerUseCaseImpl(appContext: Context) : WorkerControllerUseCase {

    private val workerTime: Long = appContext.getString(R.string.worker_time).toLong()

    var constraints: Constraints = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Constraints.Builder()
            .setRequiresDeviceIdle(false)
            .build()
    } else {
        Constraints.Builder()
            .setRequiresCharging(false)
            .build()
    }
    var myWorkNewsRequest = PeriodicWorkRequest.Builder(
        WorkerNotificationNews::class.java,
        workerTime,
        java.util.concurrent.TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .build()

    var myWorkRateRequest = PeriodicWorkRequest.Builder(
        WorkerNotificationRate::class.java,
        workerTime,
        java.util.concurrent.TimeUnit.MINUTES
    )
        .build()


    override suspend fun StartWorkerNotificationNews() {
        WorkManager.getInstance().enqueue(myWorkNewsRequest)
    }

    override suspend fun StopWorkerNotificationNews() {
        WorkManager.getInstance().cancelWorkById(myWorkNewsRequest.getId())
    }

    override suspend fun StartWorkerNotificationRate() {
        WorkManager.getInstance().enqueue(myWorkRateRequest)
    }

    override suspend fun StopWorkerNotificationRate() {
        WorkManager.getInstance().cancelWorkById(myWorkRateRequest.getId())
    }
}