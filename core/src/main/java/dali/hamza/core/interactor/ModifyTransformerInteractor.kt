package dali.hamza.core.interactor

import dali.hamza.core.repository.TransformerRepository
import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class ModifyTransformerInteractor @Inject constructor(
    private val repository: ITransformerRepository
) : UseCase<Transformer, Result<Transformer>> {
    override suspend fun invoke(parameter: Transformer): Result<Transformer> {
        return repository.update(transformer = parameter)
    }

}