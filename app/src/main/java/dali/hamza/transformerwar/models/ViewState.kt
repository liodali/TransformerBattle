package dali.hamza.transformerwar.models

import dali.hamza.domain.model.Result


enum class State {
    LOADING,ERROR,DATA
}
data class ViewState(val state: State,val data:Result<Any>?)