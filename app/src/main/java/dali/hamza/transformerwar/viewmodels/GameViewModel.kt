package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dali.hamza.core.interactor.GameInteractor
import dali.hamza.domain.model.GameResult
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel @ViewModelInject constructor(
    private val gameInteractor: GameInteractor
) : ViewModel() {

    private val listTransformers: MutableLiveData<List<Transformer>> = MutableLiveData()

    private val liveDataGame: LiveData<Result<GameResult>> =
        Transformations.switchMap(listTransformers) {
            startGame(it)
        }


    fun setListTransformers(transformers: List<Transformer>) {
        listTransformers.value = transformers
    }

    fun getGame(): LiveData<Result<GameResult>> {
        return liveDataGame
    }

    private fun startGame(transformers: List<Transformer>): LiveData<Result<GameResult>> {
        val liveData: MutableLiveData<Result<GameResult>> = MutableLiveData()
        viewModelScope.launch {
            val result = gameInteractor.invoke(transformers)

            withContext(Dispatchers.Main) {
                result.collect { game->
                    liveData.value = game
                }

            }

        }
        return liveData
    }


}