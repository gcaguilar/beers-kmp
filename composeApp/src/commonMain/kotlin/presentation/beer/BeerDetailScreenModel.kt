package presentation.beer

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.BeerDetail
import domain.GetBeerDetail
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BeerDetailScreenModel(
    private val getBeerDetail: GetBeerDetail,
) : StateScreenModel<BeerDetailScreenModel.UIState>(UIState.Loading) {
    sealed class UIState {
        data object Loading : UIState()
        data object Error : UIState()
        data class Success(val beerDetail: BeerDetail) : UIState()
    }

    fun getBeer(bid: String) {
        screenModelScope.launch {
            getBeerDetail(bid).fold(
                onFailure = { _ ->
                    mutableState.update {
                        UIState.Error
                    }
                },
                onSuccess = { beerDetail ->
                    mutableState.update {
                        UIState.Success(beerDetail)
                    }
                }
            )
        }
    }
}
