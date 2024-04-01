package presentation.search

import domain.Beer
import domain.SearchBeer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

sealed class UIState {
    data object Loading : UIState()
    data object Error : UIState()
    data class Success(val beers: List<Beer>) : UIState()
}

class SearchViewModel(
    private val searchBeer: SearchBeer,
    private val coroutineScope: CoroutineScope
) : KoinComponent {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun searchBeer() {
        coroutineScope.launch(Dispatchers.IO) {
            searchBeer("Caleya").fold(
                onFailure = {
                    _uiState.update {
                        UIState.Error
                    }
                },
                onSuccess = { beerList ->
                    _uiState.update {
                        UIState.Success(beerList)
                    }
                }
            )
        }
    }
}
