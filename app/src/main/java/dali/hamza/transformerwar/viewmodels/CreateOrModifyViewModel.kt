package dali.hamza.transformerwar.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dali.hamza.core.interactor.CreateTransformerInteractor
import dali.hamza.core.interactor.ModifyTransformerInteractor
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateOrModifyViewModel @ViewModelInject constructor(
    private val createTransformerInteractor: CreateTransformerInteractor,
    private val modifyTransformerInteractor: ModifyTransformerInteractor
) : ViewModel() {

    private val liveDataResult: MutableLiveData<Result<Transformer>> = MutableLiveData()

    fun getResult(): LiveData<Result<Transformer>> {
        return liveDataResult
    }



    fun create(transformer: Transformer) {
        viewModelScope.launch {
            val result = createTransformerInteractor.invoke(transformer)
            withContext(Main) {
                liveDataResult.value = result
            }
        }
    }

    fun modify(transformer: Transformer) {
        viewModelScope.launch {
            val result = modifyTransformerInteractor.invoke(transformer)
            withContext(Main) {
                liveDataResult.value = result
            }

        }
    }

}