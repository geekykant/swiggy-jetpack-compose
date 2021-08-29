package com.paavam.swiggyapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.core.task.SwiggyTaskManager
import com.paavam.swiggyapp.core.task.TaskState
import com.paavam.swiggyapp.di.LocalRepository
import com.paavam.swiggyapp.di.RemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.util.*

@HiltWorker
class SwiggySyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @RemoteRepository private val remoteCartRepository: SwiggyCartRepository,
    @LocalRepository private val localCartRepository: SwiggyCartRepository,
    private val swiggyTaskManager: SwiggyTaskManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = syncCart()

    private suspend fun syncCart(): Result {
        return try {
            val cartItems =
                fetchRemoteCartItems().filter { cartItem -> shouldReplaceCartItem(cartItem.foodId) }

            localCartRepository.addUsersCartFoods(cartItems)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun shouldReplaceCartItem(foodId: Long): Boolean {
        val taskId = swiggyTaskManager.getTaskIdFromCartItemId(foodId)
        val state = swiggyTaskManager.getTaskState(taskId)

        return (state == null || state != TaskState.SCHEDULED)
    }

    private suspend fun fetchRemoteCartItems(): List<Food> {
        return when (val response = remoteCartRepository.fetchUsersCartFoods().first()) {
            is ResponseResult.Success -> response.data
            is ResponseResult.Error -> throw Exception(response.message)
        }
    }

    private fun UUID.toLong(): Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
}
