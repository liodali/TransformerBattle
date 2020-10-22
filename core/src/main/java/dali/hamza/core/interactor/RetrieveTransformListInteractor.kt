package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class RetrieveTransformListInteractor @Inject constructor(
    private val repository: ITransformerRepository
) : UseCase<Any, Result<List<Transformer>>> {
    override suspend fun invoke(parameter: Any): Result<List<Transformer>> {
        return repository.getAll()
    }
}