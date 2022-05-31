package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.UserProfile
import com.paavam.swiggyapp.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
//    @RemoteRepository private val accountRepository: SwiggyAccountRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AccountViewModelState(uiLoadingState = UiState.loading()))
    val state: MutableStateFlow<AccountViewModelState> get() = _state

    init {
        prepareAccountData()
    }

    private fun prepareAccountData() {
        val user = UserProfile(
            "Jeff Bezos",
            "98129812981",
            PreviewData.prepareUsersAddresses()[0],
            "mynamesjeff@gmail.com"
        )
        _state.update {
            it.copy(user = user)
        }
    }
}

data class AccountViewModelState(
    val uiLoadingState: UiState<String>,
    val user: UserProfile? = null
)