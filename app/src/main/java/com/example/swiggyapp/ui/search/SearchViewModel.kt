package com.example.swiggyapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _searchText = MutableLiveData("")
    val searchText: LiveData<String> = _searchText

    // onSearchTextChange is an event we're defining that the UI can invoke
    // (events flow up from UI)
    fun onSearchTextChange(newSearchText: String) {
        _searchText.value = newSearchText
    }
}
