package com.paavam.swiggyapp.core.task

import com.paavam.swiggyapp.core.SwiggyAddressTask
import java.util.*
import javax.inject.Singleton

@Singleton
interface SwiggyTaskManager {

    fun syncNotes(): UUID

    fun scheduleAddressTask(swiggyAddressTask: SwiggyAddressTask): UUID

    fun getTaskState(taskId: UUID): UUID

    fun observeTask(taskId: UUID): UUID

    fun abortAllTasks()
}