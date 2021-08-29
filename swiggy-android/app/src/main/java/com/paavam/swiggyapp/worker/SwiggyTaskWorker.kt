package com.paavam.swiggyapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.SwiggyTaskAction
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.di.LocalRepository
import com.paavam.swiggyapp.di.RemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.map

@HiltWorker
class SwiggyTaskWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    @RemoteRepository private val remoteCartRepository: SwiggyCartRepository,
    @LocalRepository private val localCartRepository: SwiggyCartRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= MAX_RUN_ATTEMPTS) return Result.failure()

        val cartItemId = getCartItemId()

        return when (getTaskAction()) {
            SwiggyTaskAction.CREATE -> addCartItem(cartItemId)
            SwiggyTaskAction.UPDATE -> updateCartItem(cartItemId)
            SwiggyTaskAction.DELETE -> deleteCartItem(cartItemId)
        }
    }

    private suspend fun deleteCartItem(foodId: String): Result {
        val response = remoteCartRepository.deleteCartItem(foodId.toLong())
        return if (response is ResponseResult.Success) Result.success() else Result.retry()
    }

    private suspend fun updateCartItem(foodId: String): Result {
        val cartItem = fetchLocalFoodItem(foodId)
        val response = remoteCartRepository.updateUsersCartFood(foodId.toLong(), cartItem)
        return if (response is ResponseResult.Success) Result.success() else Result.retry()
    }

    private suspend fun addCartItem(foodId: String): Result {
        val foodItem = fetchLocalFoodItem(foodId)
        val response = remoteCartRepository.addUsersCartFood(foodItem)

        return if (response is ResponseResult.Success) {
            localCartRepository.updateUsersCartFood(foodId.toLong(), foodItem)
            Result.success()
        } else Result.failure()
    }

    private suspend fun fetchLocalFoodItem(foodId: String): Food {
        localCartRepository.fetchUsersCartFoodById(foodId)
            .map {
                if (it is ResponseResult.Success) {
                    return@map it.data
                } else {
                    throw IllegalStateException("Error occurred")
                }
            }
        throw IllegalStateException("Local data retrieval problem")
    }

    private fun getCartItemId(): String = inputData.getString(KEY_CART_ID)
        ?: throw IllegalStateException("$KEY_CART_ID should be provided as input data.")

    private fun getTaskAction(): SwiggyTaskAction =
        inputData.getEnum<SwiggyTaskAction>(KEY_TASK_TYPE)
            ?: throw IllegalStateException("$KEY_TASK_TYPE should be provided as input data.")

    companion object {
        const val MAX_RUN_ATTEMPTS = 3
        const val KEY_CART_ID = "cart_id"
        const val KEY_TASK_TYPE = "noty_task_type"
    }


    inline fun <reified T : Enum<T>> Data.getEnum(key: String): T? {
        val enumValue = getString(key)
        return runCatching { enumValueOf<T>(enumValue!!) }.getOrNull()
    }

}
