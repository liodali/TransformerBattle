package dali.hamza.core.repository

import dali.hamza.domain.model.Result

abstract  class BaseRepository{

    /**
     * Use this when communicating only with the api service
     */
    protected suspend  fun <T:Any> fetchData(dataProvider: () -> Result<T>): Result<T> {
        return dataProvider()
    }
}