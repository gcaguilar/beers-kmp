package presentation.detail

import androidx.lifecycle.ViewModel
import domain.GetBeerDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import domain.BeerDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

sealed class UIState {
    data object Loading : UIState()
    data object Error : UIState()
    data class Success(val beerDetail: BeerDetail) : UIState()
}

class DetailViewModel(
    private val getBeerDetail: GetBeerDetail,
    private val coroutineScope: CoroutineScope
) : ViewModel(), KoinComponent {
    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun getBeer(bid: String) {
        coroutineScope.launch(Dispatchers.IO) {
            getBeerDetail(bid).fold(
                onFailure = { _ ->
                    _uiState.update {
                        UIState.Error
                    }
                },
                onSuccess = { beerDetail ->
                    _uiState.update {
                        UIState.Success(beerDetail)
                    }
                }
            )
        }
    }
}