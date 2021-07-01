package thesis_project.data.worker

import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import thesis_project.domain.use_case.WorkerControllerUseCase


class WorkerControllerUseCaseImpl:WorkerControllerUseCase {
    var myWorkRequest = PeriodicWorkRequest.Builder(WorkerNotificationNews::class.java,
        60,
        java.util.concurrent.TimeUnit.MINUTES
    )
        .build()

    override suspend fun StartWorkerNotificationNews() {
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    override suspend fun StopWorkerNotificationNews() {
        WorkManager.getInstance().cancelWorkById(myWorkRequest.getId());
    }
}