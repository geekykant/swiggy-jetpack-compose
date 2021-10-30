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
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> get() = _state

    private val refreshing = MutableStateFlow(false)

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
            delay(6800)
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
            delay(1800)
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
        refreshing.value = true
        val announcementFlow: SharedFlow<String> =
            flowOf(UiState.success("As per state mandates, we will be operational till 8:00 PM"))
                .map { it.data }
                .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

        viewModelScope.launch {
            combine(
                propsRepository.fetchHelloBarContent(),
                propsRepository.fetchTilesContent(),
                announcementFlow,
                restaurantRepository.fetchRestaurantsList()
            ) { hello, tiles, msg, topPicksRest ->
                Quad(hello, tiles, msg, topPicksRest)
            }.onStart {
                _state.value.initialLoadingStatus.value = UiState.loading()
                delay(5000)
            }.collect {
                val helloBarMessagesState = it.first
                val quickTilesState = it.second
                val msg = it.third
                val topPicksRestsState = it.fourth

                when {
                    topPicksRestsState is ResponseResult.Success &&
                            helloBarMessagesState is ResponseResult.Success
                            && quickTilesState is ResponseResult.Success -> {
                        _state.value.helloBarMessages.value = helloBarMessagesState.data
                        _state.value.quickTiles.value = quickTilesState.data
                        _state.value.announcementMsg.value = msg
                        _state.value.topPicksRestaurants.value = topPicksRestsState.data
                        _state.value.initialLoadingStatus.value = UiState.success("success")
                    }
                    else -> {
                        _state.value.initialLoadingStatus.value = UiState.failed("failed")
                    }
                }
            }.also {
                refreshing.value = false
            }
        }
    }

    fun refresh(force: Boolean = true) {
        viewModelScope.launch {
            runCatching {
                delay(3000)
                prepareHomeData()
            }
        }
    }
}

data class HomeViewState(
    var helloBarMessages: MutableStateFlow<List<HelloBar>> = MutableStateFlow(emptyList()),
    var quickTiles: MutableStateFlow<List<QuickTile>> = MutableStateFlow(emptyList()),
    var announcementMsg: MutableStateFlow<String> = MutableStateFlow(""),
    var topPicksRestaurants: MutableStateFlow<List<Restaurant>> = MutableStateFlow(emptyList()),
    val initialLoadingStatus: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.loading()),
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)