package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Cuisine
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.repository.SwiggyCuisineRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyPropsRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyRestaurantRepository
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.di.RemoteRepository
import com.paavam.swiggyapp.ui.utils.Quad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @RemoteRepository private val restaurantRepository: SwiggyRestaurantRepository,
    @RemoteRepository private val cuisineRepository: SwiggyCuisineRepository,
    @RemoteRepository private val propsRepository: SwiggyPropsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewModelState(
        swiggyAvailableInArea = true,
        uiLoadingState = UiState.loading())
    )

    val state = _state
        .map { it.getUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _state.value.getUiState()
        )

    val nearbyRestaurants: SharedFlow<UiState<List<Restaurant>>> = restaurantRepository
        .fetchRestaurantsList()
        .map { result ->
            when (result) {
                is ResponseResult.Success -> UiState.success(result.data)
                is ResponseResult.Error -> UiState.failed(result.message)
            }
        }.onStart {
            emit(UiState.loading())
//            delay(800)
        }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val latestOnBlockRestaurants: SharedFlow<UiState<List<Restaurant>>> = restaurantRepository
        .fetchRestaurantsList()
        .map { result ->
            when (result) {
                is ResponseResult.Success -> UiState.success(result.data)
                is ResponseResult.Error -> UiState.failed(result.message)
            }
        }.onStart {
            emit(UiState.loading())
            delay(6000)
        }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val topOfferRestaurants: SharedFlow<UiState<List<Restaurant>>> = restaurantRepository
        .fetchRestaurantsList()
        .map { result ->
            when (result) {
                is ResponseResult.Success -> UiState.success(result.data)
                is ResponseResult.Error -> UiState.failed(result.message)
            }
        }.onStart {
            emit(UiState.loading())
//            delay(8000)
        }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val popularCurations: SharedFlow<UiState<List<Cuisine>>> = cuisineRepository
        .fetchPopularCuisines()
        .map { result ->
            when (result) {
                is ResponseResult.Success -> UiState.success(result.data)
                is ResponseResult.Error -> UiState.failed(result.message)
            }
        }.onStart {
            emit(UiState.loading())
//            delay(800)
        }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        prepareHomeData()
    }

    private fun prepareHomeData() {
        val announcementFlow: SharedFlow<String> =
            flowOf(
                UiState.success(
                    listOf("", "As per state mandates, we will be operational till 8:00 PM").random()
                )
            ).map { it.data }.shareIn(viewModelScope, SharingStarted.WhileSubscribed())

        viewModelScope.launch {
            combine(
                propsRepository.fetchHelloBarContent(),
                propsRepository.fetchTilesContent(),
                announcementFlow,
                restaurantRepository.fetchRestaurantsList()
            ) { hello, tiles, msg, topPicksRest ->
                Quad(hello, tiles, msg, topPicksRest)
            }.onStart {
                delay(800)
            }.collect {
                val helloBarMessagesState = it.first
                val quickTilesState = it.second
                val msg = it.third
                val topPicksRestsState = it.fourth

                when {
                    topPicksRestsState is ResponseResult.Success &&
                            helloBarMessagesState is ResponseResult.Success
                            && quickTilesState is ResponseResult.Success -> {

                        _state.update { state ->
                            state.copy(
                                helloBarMessages = helloBarMessagesState.data,
                                quickTiles = quickTilesState.data,
                                announcementMsg = msg,
                                topPicksRestaurants = topPicksRestsState.data,
                                uiLoadingState = UiState.success("success")
                            )
                        }
                    }
                    else -> {
                        _state.update { state -> state.copy(uiLoadingState = UiState.failed("Failed retrieving!")) }
                    }
                }
            }
        }
    }

    fun refresh() {
        _state.update { it.copy(uiLoadingState = UiState.loading()) }
        prepareHomeData()
    }
}

sealed interface HomeUiState {
    val uiState: UiState<String>
    val errorMessages: List<String>

    data class SwiggyNotAvailableInArea(
        override val errorMessages: List<String>,
        override val uiState: UiState<String>
    ): HomeUiState

    data class SwiggyAvailableLoadHomeScreen(
        override val uiState: UiState<String>,
        override val errorMessages: List<String>,
        val helloBarMessages: List<HelloBar>,
        val quickTiles: List<QuickTile>,
        val announcementMsg: String,
        val topPicksRestaurants: List<Restaurant>,
    ): HomeUiState

}

private data class HomeViewModelState(
    val swiggyAvailableInArea: Boolean,
    val uiLoadingState: UiState<String>,
    var helloBarMessages: List<HelloBar> = emptyList(),
    var quickTiles: List<QuickTile> = emptyList(),
    var announcementMsg: String = "",
    var topPicksRestaurants: List<Restaurant> = emptyList(),
    val initialLoadingStatus: UiState<String> = UiState.loading(),
    val errorMessage: String? = null
){
    fun getUiState(): HomeUiState =
        when(swiggyAvailableInArea){
            true -> {
                HomeUiState.SwiggyAvailableLoadHomeScreen(
                    uiState = uiLoadingState,
                    errorMessages = emptyList(),
                    helloBarMessages = helloBarMessages,
                    quickTiles = quickTiles,
                    announcementMsg = announcementMsg,
                    topPicksRestaurants = topPicksRestaurants
                )
            }
            false -> {
                HomeUiState.SwiggyNotAvailableInArea(
                    uiState = uiLoadingState,
                    errorMessages = listOf("Swiggy is Not available in your area. Wait for the awesomeness. We will be there!")
                )
            }
        }
}