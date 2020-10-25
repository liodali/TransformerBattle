package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.interactor.VoidUseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.repository.IAppRepository
import javax.inject.Inject

/*
 *
 * fetch api allspark to retrieve the token
 * save token in sharedPreference
 *
 */

class SaveTokenInteractor @Inject constructor(
    private val repository: IAppRepository
) : VoidUseCase<Result<String>> {
    override suspend fun invoke(): Result<String> {
        return repository.retrieveToken()
    }
}