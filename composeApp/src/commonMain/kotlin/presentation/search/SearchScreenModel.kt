package presentation.search

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.Beer
import domain.BeersWithPagination
import domain.SearchBeer
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class SearchErrorType {
    data object NoItemsFound : SearchErrorType()
    data object Unknown : SearchErrorType()
}

class SearchScreenModel(
    private val searchBeer: SearchBeer,
) : StateScreenModel<SearchScreenModel.UIState>(UIState()) {
    data class UIState(
        val searchText: String? = null,
        val isLoading: Boolean = false,
        val isRequestingMoreItems: Boolean = false,
        val error: SearchErrorType? = null,
        val searchResult: List<Beer>? = null,
        val nextPage: Int? = null
    )

    fun onInputTextChange(inputText: String?) {
        mutableState.update { uiState ->
            uiState.copy(
                searchText = inputText
            )
        }
    }

    fun searchBeer() {
        state.value.searchText?.let {
            mutableState.update {
                state.value.copy(
                    error = null, isLoading = true, searchResult = null
                )
            }
            screenModelScope.launch {
                searchBeer(state.value.searchText!!).fold(onFailure = {
                    onFailureSearchResult(it)
                }, onSuccess = { beerWithPagination ->
                    onSuccessSearchResult(beerWithPagination)
                })
            }
        }
    }

    fun requestNextPage() {
        mutableState.update {
            state.value.copy(
                isLoading = false,
                isRequestingMoreItems = true,
            )
        }
        screenModelScope.launch {
            searchBeer(state.value.searchText!!, state.value.nextPage!!).fold(onFailure = {
                onFailureSearchResult(it)
            }, onSuccess = { beerWithPagination ->
                onSuccessSearchResult(beerWithPagination)
            })
        }
    }

    fun onClearInputText() {
        mutableState.update {
            state.value.copy(
                searchText = null
            )
        }
    }

    fun processError() {
        mutableState.update {
            state.value.copy(
                error = null
            )
        }
    }

    private fun onSuccessSearchResult(beerWithPagination: BeersWithPagination) {
        mutableState.update {
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
        Napier.e("Error while searching $error")
        mutableState.update {
            state.value.copy(
                error = SearchErrorType.Unknown, isLoading = false, isRequestingMoreItems = false, searchResult = null
            )
        }
    }
}
