package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dali.hamza.core.interactor.CreateTransformerInteractor
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import kotlinx.coroutines.launch

class CreateOrModifyViewModel @ViewModelInject constructor(
    private val createTransformerInteractor: CreateTransformerInteractor
) : ViewModel() {

   private val liveDataResult: MutableLiveData<Result<String>> = MutableLiveData()

    fun getResult():LiveData<Result<String>>{
        return liveDataResult
    }

    fun create(transformer: Transformer) {
        viewModelScope.launch {
            val result = createTransformerInteractor.invoke(transformer)
            liveDataResult.postValue(result)
        }
    }


}