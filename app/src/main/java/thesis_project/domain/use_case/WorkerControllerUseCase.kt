package thesis_project.domain.use_case

interface WorkerControllerUseCase {
    suspend fun StartWorkerNotificationNews()
    suspend fun StopWorkerNotificationNews()
    suspend fun StartWorkerNotificationRate()
    suspend fun StopWorkerNotificationRate()
}