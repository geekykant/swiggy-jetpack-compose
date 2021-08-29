package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.offer.model.Offer
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.di.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    @RemoteRepository private val cartRepository: SwiggyCartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CartViewState())
    val state: StateFlow<CartViewState> get() = _state

    private val refreshing = MutableStateFlow(false)

    val cartFoods: SharedFlow<UiState<List<Food>>> = cartRepository
        .fetchUsersCartFoods()
        .map { result ->
            when (result) {
                is ResponseResult.Success -> UiState.success(result.data)
                is ResponseResult.Error -> UiState.failed(result.message)
            }
        }.onStart { emit(UiState.loading()) }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        prepareCartData()
    }

    private fun prepareCartData() {
        viewModelScope.launch {
//            val foods = when (val foodList = cartRepository.fetchUsersCartFoods()) {
//                is ResponseResult.Success -> foodList.data
//                is ResponseResult.Error -> throw Exception(foodList.message) /* Throw error message */
//            }

//            val foods = PreviewData.prepareCartFoods()

            val cartAmount = CartAmount(982.00f, 35.00f, 0, 25f)

            _state.value = CartViewState(
//                cartFoodList = foods,
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
//    val cartFoodList: List<Food> = emptyList(),
    val mainRestaurant: Restaurant? = null,
    val isShopOpen: Boolean = true,
    val isOfferApplied: Boolean = false,
    val selectedOffer: Offer? = null,
    val totalAmount: Float? = null,
    val refreshing: Boolean = false,
)