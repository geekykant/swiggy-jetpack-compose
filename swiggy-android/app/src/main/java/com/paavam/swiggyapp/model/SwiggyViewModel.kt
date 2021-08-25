package com.paavam.swiggyapp.model

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.repository.AppRepository
import com.paavam.swiggyapp.repository.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@HiltViewModel
class SwiggyViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(SwiggyAppState())
    val viewState: StateFlow<SwiggyAppState> get() = _viewState

    private val _askAddressModal = MutableStateFlow(false)
    val askAddressModal: StateFlow<Boolean> get() = _askAddressModal

    init {
        fetchInitState()
    }

    private fun fetchInitState() {
        viewModelScope.launch {
            val userAddressList = when (val addresses = appRepository.fetchUsersAddress()) {
                is ResponseResult.Success -> addresses.data
                is ResponseResult.Error -> emptyList() /* Throw error message */
            }

            _viewState.value = SwiggyAppState(
                userAddressList = userAddressList
            )
        }
    }

    fun changeAddressSheetState(show: Boolean) {
        _askAddressModal.value = show
    }

}

@ExperimentalMaterialApi
class SwiggyAppState(
    val userAddressList: List<UserAddress> = emptyList(),
    val chosenAddress: UserAddress? = null
)