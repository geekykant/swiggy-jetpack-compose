package com.paavam.swiggyapp.task

import androidx.work.*
import com.paavam.swiggyapp.core.SwiggyCartTask
import com.paavam.swiggyapp.core.task.SwiggyTaskManager
import com.paavam.swiggyapp.core.task.TaskState
import com.paavam.swiggyapp.worker.SwiggySyncWorker
import com.paavam.swiggyapp.worker.SwiggyTaskWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SwiggyTaskManagerImpl @Inject constructor(
    private val workManager: WorkManager
) : SwiggyTaskManager {

    override fun syncCart(): UUID {
        val swiggySyncWorker = OneTimeWorkRequest
            .Builder(SwiggySyncWorker::class.java)
            .setConstraints(getRequiredConstraints())
            .build()

        workManager.enqueueUniqueWork(
            SYNC_NAME,
            ExistingWorkPolicy.REPLACE,
            swiggySyncWorker
        )
        return swiggySyncWorker.id
    }

    override fun scheduleTask(swiggyCartTask: SwiggyCartTask): UUID {
        val swiggyTaskWorker = OneTimeWorkRequest
            .Builder(SwiggySyncWorker::class.java)
            .setConstraints(getRequiredConstraints())
            .setInputData(generateData(swiggyCartTask))
            .build()

        workManager.enqueueUniqueWork(
            getTaskIdFromCartItemId(swiggyCartTask.foodId).toString(),
            ExistingWorkPolicy.REPLACE,
            swiggyTaskWorker
        )

        return swiggyTaskWorker.id
    }

    fun <T> Data.Builder.putEnum(key: String, value: T) = apply { putString(key, value.toString()) }

    private fun generateData(swiggyCartTask: SwiggyCartTask) = Data.Builder()
        .putString(SwiggyTaskWorker.KEY_CART_ID, swiggyCartTask.foodId.toString())
        .putEnum(SwiggyTaskWorker.KEY_TASK_TYPE, swiggyCartTask.action)
        .build()

    private fun getRequiredConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override fun getTaskState(taskId: Long): TaskState? = runCatching {
        workManager.getWorkInfoById(UUID.fromString(taskId.toString()))
            .get()
            .let { mapWorkInfoStateToTaskState(it.state) }
    }.getOrNull()

    override fun observeTask(taskId: Long): Flow<TaskState> = flow {
        workManager.getWorkInfoByIdLiveData(UUID.fromString(taskId.toString())).observeForever {
            mapWorkInfoStateToTaskState(it.state)
        }
    }

    private fun mapWorkInfoStateToTaskState(state: WorkInfo.State): TaskState = when (state) {
        WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING, WorkInfo.State.BLOCKED -> TaskState.SCHEDULED
        WorkInfo.State.CANCELLED -> TaskState.CANCELLED
        WorkInfo.State.FAILED -> TaskState.FAILED
        WorkInfo.State.SUCCEEDED -> TaskState.COMPLETED
    }

    override fun abortAllTasks() {
        workManager.cancelAllWork()
    }

    companion object {
        private const val SYNC_NAME = "Task-Swiggy-Sync"
    }

}