package com.example.swiggyapp.ui.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiggyapp.data.Restaurant
import com.example.swiggyapp.data.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel(
    private val restaurantRepository: RestaurantRepository = RestaurantRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RestaurantViewState())
    val state: StateFlow<RestaurantViewState> get() = _state

    private val _expandedSectionIds = MutableStateFlow(listOf<Int>())
    private val refreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _state.value = RestaurantViewState(
                restaurant = restaurantRepository.prepareARestaurant(),
                restaurantFoods = restaurantRepository.prepareAllRestaurantFoods(),
                expandedSectionIds = _expandedSectionIds,
                refreshing = refreshing.value,
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
    var expandedSectionIds: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList()),
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)
