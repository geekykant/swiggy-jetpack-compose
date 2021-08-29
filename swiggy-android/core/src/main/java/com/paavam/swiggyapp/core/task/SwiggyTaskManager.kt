package com.paavam.swiggyapp.core.task

import com.paavam.swiggyapp.core.SwiggyCartTask
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Singleton

@Singleton
interface SwiggyTaskManager {

    fun syncCart(): UUID

    fun scheduleTask(swiggyCartTask: SwiggyCartTask): UUID

    fun getTaskState(taskId: Long): TaskState?

    fun observeTask(taskId: Long): Flow<TaskState>

    fun abortAllTasks()

    fun getTaskIdFromCartItemId(cartItemId: Long) = cartItemId
}