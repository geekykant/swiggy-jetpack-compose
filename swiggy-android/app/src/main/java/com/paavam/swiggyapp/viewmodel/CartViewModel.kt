package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.offer.model.Offer
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyRestaurantRepository
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.di.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    @RemoteRepository private val cartRepository: SwiggyCartRepository,
    @RemoteRepository private val restaurantRepository: SwiggyRestaurantRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CartViewState())
    val state: StateFlow<CartViewState> get() = _state

    private val _userMessageToRestaurant = MutableLiveData("")
    val userMessageToRestaurant: LiveData<String> = _userMessageToRestaurant

    fun onUserMessageToRestaurantChange(newText: String) {
        _userMessageToRestaurant.value = newText
    }

    init {
        prepareCartData()
    }

    private fun prepareCartData() {
        viewModelScope.launch {
            combine(
                cartRepository.fetchUsersCartFoods(),
                restaurantRepository.fetchThisRestaurant()
            ) { cartFoods, restaurant ->
                Pair(cartFoods, restaurant)
            }.onStart {
                delay(800)
            }.collect { results ->
                val cartFoods = results.first
                val restaurant = results.second

                if (cartFoods is ResponseResult.Success && restaurant is ResponseResult.Success) {
                    _state.value.cartFoodList.value = cartFoods.data
                    _state.value.mainRestaurant.value = restaurant.data
                    notifyCartAmountChanged()
                    _state.value.initialLoadingStatus.value = UiState.success("success")
                } else {
                    _state.value.initialLoadingStatus.value = UiState.failed("failed")
                }
            }
        }
    }

    private fun notifyCartAmountChanged() {
        val newPriceBeforeTax = _state.value.cartFoodList.value
            .sumOf { it.price * it.quantityInCart }
            .toFloat()
        val cartAmount = CartAmount(
            newPriceBeforeTax,
            35.00f,
            0,
            25f
        )
        _state.value.cartAmount.value = cartAmount
        _state.value.totalAmount.value = cartAmount.toPayTotal()
    }

    fun notifyCartItemChange(food: Food, updatedQuantity: Int) {
        viewModelScope.launch {
            val response = cartRepository.updateUsersCartFood(food = food, foodId = food.foodId)
            if (response is ResponseResult.Success) {
                _state.value.cartFoodList.value =
                    when (updatedQuantity) {
                        0 -> _state.value.cartFoodList.value.toMutableList().apply { remove(food) }
                        else -> _state.value.cartFoodList.value.map {
                            if (it.foodId == food.foodId) {
                                it.copy(quantityInCart = updatedQuantity)
                            } else {
                                it
                            }
                        }
                    }
                notifyCartAmountChanged()
            } else {
                //throw error
            }
        }
    }

    fun notifyCustomizationAdded() {

    }
}

data class CartAmount(
    var itemTotal: Float = 0f,
    var deliveryFee: Float = 0f,
    var deliveryTip: Int = 0,
    var packingCharges: Float = 25f,
    private var restaurantGSTRate: Float = 18.00f
) {
    private val beforeTaxAmount = itemTotal + deliveryTip + deliveryFee

    private fun calculatePayableGSTAmount(): Float {
        return (restaurantGSTRate * beforeTaxAmount) / 100f
    }

    fun calculateTotalTaxCharges(): Float {
        return packingCharges + calculatePayableGSTAmount()
    }

    fun toPayTotal(): Float {
        return (beforeTaxAmount + calculateTotalTaxCharges()).roundToInt().toFloat()
    }
}

data class CartViewState(
    val cartAmount: MutableStateFlow<CartAmount?> = MutableStateFlow(null),
    val cartFoodList: MutableStateFlow<List<Food>> = MutableStateFlow(emptyList()),
    val mainRestaurant: MutableStateFlow<Restaurant?> = MutableStateFlow(null),
    val isShopOpen: Boolean = true,
    val isOfferApplied: Boolean = false,
    val selectedOffer: Offer? = null,
    val totalAmount: MutableStateFlow<Float?> = MutableStateFlow(null),
    val refreshing: Boolean = false,
    val initialLoadingStatus: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.loading()),
)