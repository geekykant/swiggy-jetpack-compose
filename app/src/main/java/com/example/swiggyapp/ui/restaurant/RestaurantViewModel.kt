package com.example.swiggyapp.ui.restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RestaurantViewModel() : ViewModel() {
    private val _restaurantFoods = MutableStateFlow(RestaurantFoodModel(null, null))
    val restaurantFoods: MutableStateFlow<RestaurantFoodModel> get() = _restaurantFoods

    private val _expandedFoodSectionIdsList = MutableStateFlow(listOf<Int>())
    val expandedFoodSectionIdsList: StateFlow<List<Int>> get() = _expandedFoodSectionIdsList

    init {
        getRestaurantDetails()
        getRestaurantFoodData()
    }

    private fun getRestaurantDetails() {
        viewModelScope.launch(Dispatchers.Default) {
            //fetch restaurant details
//            val restaurant = Restaurant(
//                "Aryaas",
//                "South Indian, Chineese, Arabian, North India",
//                "Kakkanad",
//                "7.2 kms",
//                4.2f,
//                53,
//                400,
//                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
//                listOf(Offer(R.drawable.ic_offers_filled, 40, 80, "40METOO", 129)),
//                OfferSnack("40% OFF", OfferSnackType.BASIC)
//            )
        }
    }

    private fun getRestaurantFoodData() {
        viewModelScope.launch(Dispatchers.Default) {
            val subFoodList = prepareAllRestaurantFoods()
            //by default expand drop-downs
//            _expandedFoodSectionIdsList.emit(subFoodList.mainFoodSections.orEmpty().flatMap { it.mainFoodSections!! }.subList(0,1).map { it.subSectionId }.toList())
            subFoodList.recommendedFoods?.let {
                _expandedFoodSectionIdsList.emit(listOf(it.subSectionId))
            }
            _restaurantFoods.emit(subFoodList)
        }
    }

    fun onSectionExpanded(sectionId: Int) {
        _expandedFoodSectionIdsList.value =
            _expandedFoodSectionIdsList.value.toMutableList().also { list ->
                if (list.contains(sectionId)) list.remove(sectionId) else list.add(sectionId)
            }
    }
}