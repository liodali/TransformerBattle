package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class CreateTransformerInteractor @Inject constructor(val repository: ITransformerRepository) :UseCase<Transformer,Result<String>> {
    override suspend fun invoke(parameter: Transformer): Result<String> {
        return repository.insertTransformer(parameter)
    }
}