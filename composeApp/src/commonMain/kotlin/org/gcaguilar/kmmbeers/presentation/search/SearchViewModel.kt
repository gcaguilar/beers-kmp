package org.gcaguilar.kmmbeers.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.gcaguilar.kmmbeers.domain.Beer
import org.gcaguilar.kmmbeers.domain.BeersWithPagination
import org.gcaguilar.kmmbeers.domain.SearchBeer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class SearchErrorType {
    data object NoItemsFound : SearchErrorType()
    data object Unknown : SearchErrorType()
}

class SearchViewModel(
    private val searchBeer: SearchBeer,
) : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> = _state

    data class UIState(
        val searchText: String? = null,
        val isLoading: Boolean = false,
        val isRequestingMoreItems: Boolean = false,
        val error: SearchErrorType? = null,
        val searchResult: List<Beer>? = null,
        val nextPage: Int? = null
    )

    fun onInputTextChange(inputText: String?) {
        _state.update { uiState ->
            uiState.copy(
                searchText = inputText
            )
        }
    }

    fun searchBeer() {
        state.value.searchText?.let {
            _state.update {
                state.value.copy(
                    error = null, isLoading = true, searchResult = null
                )
            }
            viewModelScope.launch {
                searchBeer(state.value.searchText!!).fold(onFailure = {
                    onFailureSearchResult(it)
                }, onSuccess = { beerWithPagination ->
                    onSuccessSearchResult(beerWithPagination)
                })
            }
        }
    }

    fun requestNextPage() {
        _state.update {
            state.value.copy(
                isLoading = false,
                isRequestingMoreItems = true,
            )
        }
        viewModelScope.launch {
            searchBeer(state.value.searchText!!, state.value.nextPage!!).fold(onFailure = {
                onFailureSearchResult(it)
            }, onSuccess = { beerWithPagination ->
                onSuccessSearchResult(beerWithPagination)
            })
        }
    }

    fun onClearInputText() {
        _state.update {
            state.value.copy(
                searchText = null
            )
        }
    }

    fun processError() {
        _state.update {
            state.value.copy(
                error = null
            )
        }
    }

    private fun onSuccessSearchResult(beerWithPagination: BeersWithPagination) {
        _state.update {
            state.value.copy(
                isLoading = false,
                error = null,
                isRequestingMoreItems = false,
                searchResult = it.searchResult?.plus(beerWithPagination.beerList) ?: beerWithPagination.beerList,
                nextPage = beerWithPagination.nextPage
            )
        }
    }

    private fun onFailureSearchResult(error: Throwable) {
        // Napier.e("Error while searching $error")
        _state.update {
            state.value.copy(
                error = SearchErrorType.Unknown, isLoading = false, isRequestingMoreItems = false, searchResult = null
            )
        }
    }
}
