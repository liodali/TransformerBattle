package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class CreateTransformerInteractor @Inject constructor(
    val repository: ITransformerRepository,
) :UseCase<Transformer,Result<Transformer>> {
    override suspend fun invoke(parameter: Transformer): Result<Transformer> {
        return repository.insertTransformer(parameter)
    }
}