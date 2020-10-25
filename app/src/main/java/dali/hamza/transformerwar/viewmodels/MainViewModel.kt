package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dali.hamza.core.interactor.RetrieveListTransformersInteractor
import dali.hamza.domain.model.Success
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.models.ViewState
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val retrieveListTransformersInteractor: RetrieveListTransformersInteractor
) :ViewModel() {

    private val liveDataViewState: LiveData<ViewState> = Transformations.map(retrieveListTransformers()){
        it
    }

    fun getViewState():LiveData<ViewState> = liveDataViewState


    fun retrieveListTransformers():MutableLiveData<ViewState>{
        val liveDataViewState:MutableLiveData<ViewState> = MutableLiveData()
        liveDataViewState.value= ViewState(State.LOADING,null)
        viewModelScope.launch {
          val result=  retrieveListTransformersInteractor.invoke()
            if(result is Success){
                liveDataViewState.postValue(ViewState(State.DATA,result))
            }else{
                liveDataViewState.postValue(ViewState(State.ERROR,result))
            }
        }
        return  liveDataViewState
    }


}