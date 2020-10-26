package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class DeleteTransformerInteractor @Inject constructor(
    private val repository: ITransformerRepository
) : UseCase<String, Result<String>> {
    override suspend fun invoke(parameter: String): Result<String> {
        return repository.delete(parameter)
    }

}