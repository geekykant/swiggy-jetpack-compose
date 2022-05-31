package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.Offer
import com.paavam.swiggyapp.core.data.model.Restaurant
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
    private val _state = MutableStateFlow(CartViewModelState(uiLoadingState = UiState.loading()))
    val state = _state
        .map { it.getUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value.getUiState()
        )

    init {
        refreshCartPage()
        prepareCartData()
    }

    private fun refreshCartPage(){
        _state.update { it.copy(uiLoadingState = UiState.loading()) }
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
                    _state.update {
                        it.copy(
                            cartFoodList = cartFoods.data,
                            mainRestaurant = restaurant.data,
                            uiLoadingState = UiState.success("Success")
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            uiLoadingState = UiState.failed("Failed")
                        )
                    }
                }
            }
        }
    }

    fun setNewCustomerToShopKeeperMessage(newMessage: String){
        _state.update {
            it.copy(customerOptionalMessageToShopkeeper = newMessage)
        }
    }

    fun notifyCartItemChange(food: Food, updatedQuantity: Int) {
        viewModelScope.launch {
            val response = cartRepository.updateUsersCartFood(food = food, foodId = food.foodId)
            when(response){
                is ResponseResult.Success -> {
                    _state.update {
                        when(updatedQuantity){
                            0 -> it.copy(cartFoodList = it.cartFoodList.toMutableList().apply { remove(food) })
                            else -> {
                                it.copy(
                                    cartFoodList = it.cartFoodList.map {  iterFood->
                                        if(iterFood.foodId == food.foodId)
                                            iterFood.copy(quantityInCart = updatedQuantity)
                                        else
                                            iterFood
                                    }
                                )
                            }
                        }
                    }
                }
                is ResponseResult.Error -> {
                    //throw error
                    _state.update { it.copy(errorMessages = listOf(response.message)) }
                }
            }
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

    private fun calculatePayableGSTAmount(): Float = (restaurantGSTRate * beforeTaxAmount) / 100f

    fun getTotalTaxCharges(): Float = packingCharges + calculatePayableGSTAmount()

    fun toPayTotal(): Float = (beforeTaxAmount + getTotalTaxCharges()).roundToInt().toFloat()
}

sealed interface CartUiState {
    val uiLoadingState: UiState<String>
    val errorMessages: List<String>

    data class NoItemsInCart(
        override val uiLoadingState: UiState<String>,
        override val errorMessages: List<String>
    ): CartUiState

    data class HasItemsInCart(
        override val uiLoadingState: UiState<String>,
        override val errorMessages: List<String>,
        val isShopOpen: Boolean = true,
        val customerOptionalMessageToShopkeeper: String?,
        val cartAmount: CartAmount?,
        val cartFoodList: List<Food>,
        val mainRestaurant: Restaurant?,
        val isOfferApplied: Boolean,
        val selectedOffer: Offer? = null,
        val totalAmount: Float?
    ): CartUiState
}

private data class CartViewModelState(
    val uiLoadingState: UiState<String>,
    val errorMessages: List<String> = emptyList(),
    val cartFoodList: List<Food> = emptyList(),
    val mainRestaurant: Restaurant? = null,
    val isShopOpen: Boolean = false,
    val isOfferApplied: Boolean = false,
    val selectedOffer: Offer? = null,
    val totalAmount: Float? = null,
    val customerOptionalMessageToShopkeeper: String? = null
){
    fun getUiState(): CartUiState =
        if (cartFoodList.isEmpty()){
            CartUiState.NoItemsInCart(
                uiLoadingState = uiLoadingState,
                errorMessages = errorMessages
            )
        }else{
            // Calculates the total prices and updates with tax, delivery fees, packing charges
            val newTotalPriceBeforeTax = cartFoodList.sumOf { it.price * it.quantityInCart }.toFloat()
            val newCartAmount = CartAmount(
                newTotalPriceBeforeTax,
                35.00f,
                0,
                25f
            )

            CartUiState.HasItemsInCart(
                uiLoadingState = uiLoadingState,
                errorMessages = errorMessages,
                isShopOpen = isShopOpen,
                customerOptionalMessageToShopkeeper = customerOptionalMessageToShopkeeper,
                cartAmount = newCartAmount,
                cartFoodList = cartFoodList,
                mainRestaurant = mainRestaurant,
                isOfferApplied = isOfferApplied,
                selectedOffer = selectedOffer,
                totalAmount = newCartAmount.toPayTotal()
            )
        }
}