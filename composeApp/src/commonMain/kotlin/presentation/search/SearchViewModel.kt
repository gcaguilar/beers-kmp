package presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.Beer
import domain.BeersWithPagination
import domain.SearchBeer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

sealed class SearchErrorType {
    data object NoItemsFound : SearchErrorType()
    data object Unknown : SearchErrorType()
}

data class UIState(
    val searchText: String? = null,
    val isLoading: Boolean = false,
    val isRequestingMoreItems: Boolean = false,
    val error: SearchErrorType? = null,
    val searchResult: List<Beer>? = null,
    val nextPage: Int? = null
)

class SearchViewModel(
    private val searchBeer: SearchBeer,
) : ViewModel(), KoinComponent {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    fun onInputTextChange(inputText: String?) {
        _uiState.update { uiState ->
            uiState.copy(
                searchText = inputText
            )
        }
    }

    fun searchBeer() {
        uiState.value.searchText?.let {
            _uiState.update {
                uiState.value.copy(
                    error = null,
                    isLoading = true,
                    searchResult = null
                )
            }
            viewModelScope.launch {
                searchBeer(uiState.value.searchText!!).fold(
                    onFailure = {
                        onFailureSearchResult(Throwable())
                    },
                    onSuccess = { beerWithPagination ->
                        onSuccessSearchResult(beerWithPagination)
                    }
                )
            }
        }
    }

    fun requestNextPage() {
        _uiState.update {
            uiState.value.copy(
                isLoading = false,
                isRequestingMoreItems = true,
            )
        }
        viewModelScope.launch {
            searchBeer(uiState.value.searchText!!, uiState.value.nextPage!!).fold(
                onFailure = {
                    onFailureSearchResult(Throwable())
                },
                onSuccess = { beerWithPagination ->
                    onSuccessSearchResult(beerWithPagination)
                }
            )
        }
    }

    fun onClearInputText() {
        _uiState.update {
            uiState.value.copy(
                searchText = null
            )
        }
    }

    fun processError() {
        _uiState.update {
            uiState.value.copy(
                error = null
            )
        }
    }

    private fun onSuccessSearchResult(beerWithPagination: BeersWithPagination) {
        _uiState.update {
            uiState.value.copy(
                isLoading = false,
                error = null,
                isRequestingMoreItems = false,
                searchResult = it.searchResult?.plus(beerWithPagination.beerList) ?: beerWithPagination.beerList,
                nextPage = beerWithPagination.nextPage
            )
        }
    }

    private fun onFailureSearchResult(error: Throwable) {
        _uiState.update {
            uiState.value.copy(
                error = SearchErrorType.Unknown,
                isLoading = false,
                isRequestingMoreItems = false,
                searchResult = null
            )
        }
    }
}
