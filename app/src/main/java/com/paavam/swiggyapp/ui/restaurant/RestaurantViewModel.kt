package com.paavam.swiggyapp.ui.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.repository.ResponseResult
import com.paavam.swiggyapp.core.repository.RestaurantsRepository
import com.paavam.swiggyapp.data.Restaurant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel : RestaurantsRepository, ViewModel() {
    private val _state = MutableStateFlow(RestaurantViewState())
    val state: StateFlow<RestaurantViewState> get() = _state

    private val _expandedSectionIds = MutableStateFlow(listOf<Int>())
    private val _refreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            val restaurant =
                when (val restaurantResult = fetchThisRestaurant()) {
                    is ResponseResult.Success -> restaurantResult.data
                    is ResponseResult.Error -> null /* Throw error message */
                }
            val restaurantFoods = when (val restaurantFoodsResult = fetchThisRestaurantFoods()) {
                is ResponseResult.Success -> restaurantFoodsResult.data
                is ResponseResult.Error -> null /* Throw error message */
            }

            _state.value = RestaurantViewState(
                restaurant = restaurant,
                restaurantFoods = restaurantFoods,
                expandedSectionIds = _expandedSectionIds,
                refreshing = _refreshing.value,
                errorMessage = null
            )
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
