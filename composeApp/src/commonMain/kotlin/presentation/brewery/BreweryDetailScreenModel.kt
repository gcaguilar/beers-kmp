package presentation.brewery

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.Brewery
import domain.GetBreweryDetail
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BreweryDetailScreenModel(
    private val getBreweryDetail: GetBreweryDetail
) : StateScreenModel<BreweryDetailScreenModel.UIState>(UIState.Loading) {
    sealed class UIState {
        data object Loading : UIState()
        data object Error : UIState()
        data class Success(val breweryDetail: Brewery) : UIState()
    }

    suspend fun fetchBreweryDetail(id: String) {
        screenModelScope.launch {
            getBreweryDetail(id).fold(
                onSuccess = { brewery ->
                    mutableState.update {
                        UIState.Success(brewery)
                    }
                },
                onFailure = {
                    mutableState.update {
                        UIState.Error
                    }
                }
            )
        }
    }
}
