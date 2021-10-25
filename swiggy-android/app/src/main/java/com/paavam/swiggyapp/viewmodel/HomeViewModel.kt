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

    var helloBarMessages: MutableStateFlow<List<HelloBar>> = MutableStateFlow(emptyList())
    var quickTiles: MutableStateFlow<List<QuickTile>> = MutableStateFlow(emptyList())

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

//    val quickTiles: SharedFlow<UiState<List<QuickTile>>> = propsRepository
//        .fetchTilesContent()
//        .map { result ->
//            when (result) {
//                is ResponseResult.Success -> UiState.success(result.data)
//                is ResponseResult.Error -> UiState.failed(result.message)
//            }
//        }.onStart {
//            emit(UiState.loading())
////            delay(800)
//        }
//        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
//
//    val helloBarMessages: SharedFlow<UiState<List<HelloBar>>> = propsRepository
//        .fetchHelloBarContent()
//        .map { result ->
//            when (result) {
//                is ResponseResult.Success -> UiState.success(result.data)
//                is ResponseResult.Error -> UiState.failed(result.message)
//            }
//        }.onStart {
//            emit(UiState.loading())
////            delay(800)
//        }
//        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        prepareHomeData()
    }

    private fun prepareHomeData() {
        refreshing.value = true

        viewModelScope.launch {
            combine(
                propsRepository.fetchHelloBarContent(),
                propsRepository.fetchTilesContent()
            ) { hello, tiles ->
                Pair(hello, tiles)
            }.collect {
                val helloBarMessagesState = it.first
                val quickTilesState = it.second

                when {
                    helloBarMessagesState is ResponseResult.Success
                            && quickTilesState is ResponseResult.Success -> {
                        helloBarMessages.value = helloBarMessagesState.data
                        quickTiles.value = quickTilesState.data
//                        _state.value = HomeViewState(
//                            quickTiles = tiles.data,
//                            helloBarMessages = hello.data,
////                          popularCurations = popularCurations,
//                            refreshing = refreshing.value,
//                            errorMessage = null
//                        )
                    }
                    else -> {
                        /* throw error */
                    }
                }.also {
                    refreshing.value = false
                }
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
//    val quickTiles: List<QuickTile> = emptyList(),
//    val helloBarMessages: List<HelloBar> = emptyList(),
//    val popularCurations: List<Cuisine> = emptyList(),
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)