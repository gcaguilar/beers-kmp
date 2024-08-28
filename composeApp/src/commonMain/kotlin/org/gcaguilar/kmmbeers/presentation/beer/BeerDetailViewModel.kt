package org.gcaguilar.kmmbeers.presentation.beer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gcaguilar.kmmbeers.domain.BeerDetail
import org.gcaguilar.kmmbeers.domain.GetBeerDetail


class BeerDetailViewModel(
    private val getBeerDetail: GetBeerDetail,
) : ViewModel() {
    private val _state: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val state: StateFlow<UIState> = _state

    sealed class UIState {
        data object Loading : UIState()
        data object Error : UIState()
        data class Success(val beerDetail: BeerDetail) : UIState()
    }

    fun getBeer(bid: String) {
        viewModelScope.launch {
            getBeerDetail(bid).fold(
                onFailure = { _ ->
                    _state.update {
                        UIState.Error
                    }
                },
                onSuccess = { beerDetail ->
                    _state.update {
                        UIState.Success(beerDetail)
                    }
                }
            )
        }
    }
}
