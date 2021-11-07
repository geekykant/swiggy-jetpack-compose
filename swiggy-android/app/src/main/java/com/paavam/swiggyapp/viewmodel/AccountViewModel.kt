package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.UserProfile
import com.paavam.swiggyapp.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
//    @RemoteRepository private val accountRepository: SwiggyAccountRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AccountViewState())
    val state: MutableStateFlow<AccountViewState> get() = _state

    init {
        prepareAccountData()
    }

    private fun prepareAccountData() {
        viewModelScope.launch {
            //do inital tasks
        }
    }
}

data class AccountViewState(
    val user: MutableStateFlow<UiState<UserProfile>> = MutableStateFlow(UiState.success(
        UserProfile(
            "Jeff Bezos",
            "98129812981",
            PreviewData.prepareUsersAddresses()[0],
            "mynamesjeff@gmail.com"
        ))
    )
)