package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dali.hamza.core.interactor.SaveTokenInteractor
import dali.hamza.domain.model.Success
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.models.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeViewModel @ViewModelInject constructor(
    private val saveTokenInteractor: SaveTokenInteractor
) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun stateView(): LiveData<ViewState> {
        if (viewStateLiveData.value == null) {
            viewStateLiveData.value = ViewState(State.LOADING, null)
        }
        return viewStateLiveData;
    }

    fun checkToken() {
        viewModelScope.launch() {
            withContext(Dispatchers.IO) {
                val result = saveTokenInteractor.invoke()
                withContext(Dispatchers.Main) {
                    if (result is Success<String>)
                        viewStateLiveData.value = ViewState(State.DATA, result)
                    else {
                        viewStateLiveData.value = ViewState(State.ERROR, result)

                    }
                }
            }

        }
    }


}