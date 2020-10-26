package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dali.hamza.core.interactor.DeleteTransformerInteractor
import dali.hamza.core.interactor.RetrieveListTransformersInteractor
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Success
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.models.ViewState
import dali.hamza.transformerwar.utilities.SingleEventLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(
    private val retrieveListTransformersInteractor: RetrieveListTransformersInteractor,
    private val deleteTransformerInteractor: DeleteTransformerInteractor,
) : ViewModel() {

    private val liveDataViewState: LiveData<ViewState> =
        Transformations.map(retrieveListTransformers()) {
            it
        }
    private val deleteEvent: SingleEventLiveData<Result<String>> = SingleEventLiveData()

    fun getViewState(): LiveData<ViewState> = liveDataViewState

    fun getEventDelete(): SingleEventLiveData<Result<String>> = deleteEvent

    fun deleteTransformer(idTransformer: String) {
        viewModelScope.launch {
            val result = deleteTransformerInteractor.invoke(idTransformer)
            withContext(Main) {
                deleteEvent.setEvent(result)
            }
        }
    }

    fun retrieveListTransformers(): MutableLiveData<ViewState> {
        val liveDataViewState: MutableLiveData<ViewState> = MutableLiveData()
        liveDataViewState.value = ViewState(State.LOADING, null)
        viewModelScope.launch {
            val result = retrieveListTransformersInteractor.invoke()
            if (result is Success) {
                liveDataViewState.postValue(ViewState(State.DATA, result))
            } else {
                liveDataViewState.postValue(ViewState(State.ERROR, result))
            }
        }
        return liveDataViewState
    }


}