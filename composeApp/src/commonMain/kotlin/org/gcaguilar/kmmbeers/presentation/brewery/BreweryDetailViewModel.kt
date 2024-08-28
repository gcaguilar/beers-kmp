package org.gcaguilar.kmmbeers.presentation.brewery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gcaguilar.kmmbeers.domain.Brewery
import org.gcaguilar.kmmbeers.domain.GetBreweryDetail

class BreweryDetailViewModel(
    private val getBreweryDetail: GetBreweryDetail
) : ViewModel() {
    private val _state: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val state: StateFlow<UIState> = _state

    sealed class UIState {
        data object Loading : UIState()
        data object Error : UIState()
        data class Success(val breweryDetail: Brewery) : UIState()
    }

    suspend fun fetchBreweryDetail(id: String) {
        viewModelScope.launch {
            getBreweryDetail(id).fold(
                onSuccess = { brewery ->
                    _state.update {
                        UIState.Success(brewery)
                    }
                },
                onFailure = {
                    _state.update {
                        UIState.Error
                    }
                }
            )
        }
    }
}
