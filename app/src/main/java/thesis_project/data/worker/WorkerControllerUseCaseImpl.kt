package thesis_project.data.worker


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import thesis_project.domain.use_case.WorkerControllerUseCase


class WorkerControllerUseCaseImpl:WorkerControllerUseCase {

    var constraints: Constraints = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Constraints.Builder()
                .setRequiresDeviceIdle(false)
                .build()
    } else {
        Constraints.Builder()
            .setRequiresCharging(false)
            .build()
    }
    var myWorkRequest = PeriodicWorkRequest.Builder(WorkerNotificationNews::class.java,
        30,
        java.util.concurrent.TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .build()

    override suspend fun StartWorkerNotificationNews() {
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    override suspend fun StopWorkerNotificationNews() {
        WorkManager.getInstance().cancelWorkById(myWorkRequest.getId());
    }

    override suspend fun StartWorkerNotificationRate() {
        TODO("Not yet implemented")
    }

    override suspend fun StopWorkerNotificationRate() {
        TODO("Not yet implemented")
    }
}