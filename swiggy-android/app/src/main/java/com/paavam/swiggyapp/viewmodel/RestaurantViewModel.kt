package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.model.RestaurantFoodModel
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList


@Module
@InstallIn(ActivityRetainedComponent::class)
interface AssistedInjectModule

class RestaurantViewModel @AssistedInject constructor(
    @Assisted val restaurantId: Long,
//    private val swiggyRestaurantRepository: SwiggyRestaurantRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RestaurantViewState())
    val state: StateFlow<RestaurantViewState> get() = _state

    @AssistedFactory
    interface Factory {
        fun create(restaurantId: Long): RestaurantViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            restaurantId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(restaurantId) as T
            }
        }
    }

    private val _expandedSectionIds = MutableStateFlow(listOf<Int>())
    private val _refreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            val restaurant = PreviewData.prepareRestaurants().find { it.restaurantId == restaurantId }
//            PreviewData.prepareARestaurant()
////                when (val restaurantResult = swiggyRestaurantRepository.fetchThisRestaurant()) {
////                    is ResponseResult.Success -> restaurantResult.data
////                    is ResponseResult.Error -> null /* Throw error message */
//                }
            val restaurantFoods = PreviewData.prepareAllRestaurantFoods()
//                when (val restaurantFoodsResult =
//                swiggyRestaurantRepository.fetchThisRestaurantFoods()) {
//                is ResponseResult.Success -> restaurantFoodsResult.data
//                is ResponseResult.Error -> null /* Throw error message */
//            }

            _state.value = RestaurantViewState(
                restaurant = restaurant,
                restaurantFoods = restaurantFoods,
                expandedSectionIds = _expandedSectionIds,
                refreshing = _refreshing.value,
                errorMessage = null
            )
            onSectionExpanded(31)
        }
    }

    fun onSectionExpanded(sectionId: Int) {
        _expandedSectionIds.value = _expandedSectionIds.value.toMutableList().also { list ->
            if (list.contains(sectionId)) list.remove(sectionId) else list.add(sectionId)
        }
    }
}

data class RestaurantViewState(
    val restaurant: Restaurant? = null,
    val restaurantFoods: RestaurantFoodModel? = null,
    val expandedSectionIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList()),
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)