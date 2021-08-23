package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.PreviewData
import com.paavam.swiggyapp.model.Food
import com.paavam.swiggyapp.model.Offer
import com.paavam.swiggyapp.model.Restaurant
import com.paavam.swiggyapp.repository.CartRepository
import com.paavam.swiggyapp.repository.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CartViewState())
    val state: StateFlow<CartViewState> get() = _state

    private val refreshing = MutableStateFlow(false)

    init {
        prepareCartData()
    }

    private fun prepareCartData() {
        viewModelScope.launch {
            val foodsList = when (val foods = cartRepository.fetchUsersCart()) {
                is ResponseResult.Success -> foods.data
                is ResponseResult.Error -> emptyList() /* Throw error message */
            }

            val cartAmount = CartAmount(982.00f, 35.00f, 0, 25f)

            _state.value = CartViewState(
                cartFoodList = foodsList,
                mainRestaurant = PreviewData.prepareARestaurant(),
                refreshing = refreshing.value,
                totalAmount = cartAmount.toPayTotal()
            )
        }
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

    fun calculatePayableGSTAmount(): Float {
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
    val cartAmount: CartAmount? = null,
    val cartFoodList: List<Food> = emptyList(),
    val mainRestaurant: Restaurant? = null,
    val isShopOpen: Boolean = true,
    val isOfferApplied: Boolean = false,
    val selectedOffer: Offer? = null,
    val totalAmount: Float? = null,
    val refreshing: Boolean = false,
)