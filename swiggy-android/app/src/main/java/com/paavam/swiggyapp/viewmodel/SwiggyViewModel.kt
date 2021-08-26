package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.PreviewData
import com.paavam.swiggyapp.model.UserAddress
import com.paavam.swiggyapp.repository.AppRepository
import com.paavam.swiggyapp.repository.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwiggyViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(SwiggyAppState())
    val viewState: StateFlow<SwiggyAppState> get() = _viewState

    private val _askAddressModal = MutableStateFlow(true)
    val askAddressModal: StateFlow<Boolean> get() = _askAddressModal

    private val _defaultAddress =
        MutableStateFlow<UserAddress?>(PreviewData.prepareUsersAddresses()[0])
    val defaultAddress: StateFlow<UserAddress?> get() = _defaultAddress

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

    fun assignDefaultAddress(address: UserAddress?) {
        _defaultAddress.value = address
    }

}

class SwiggyAppState(
    val userAddressList: List<UserAddress> = emptyList(),
)