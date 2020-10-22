package dali.hamza.transformerwar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dali.hamza.transformerwar.models.State
import dali.hamza.transformerwar.models.ViewState
import kotlinx.coroutines.launch

class WelcomeViewModel :ViewModel() {

    private val viewStateLiveData: MutableLiveData<ViewState> = MutableLiveData()

    fun stateView():LiveData<ViewState>{
        if(viewStateLiveData.value==null){
            viewStateLiveData.value= ViewState(State.LOADING,null)
        }
        return viewStateLiveData;
    }

    fun checkToken(){
        viewModelScope.launch {

        }
    }


}