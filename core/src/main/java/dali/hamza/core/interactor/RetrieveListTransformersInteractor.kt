package dali.hamza.core.interactor

import dali.hamza.domain.interactor.VoidUseCase
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.model.TransformerList
import dali.hamza.domain.repository.ITransformerRepository
import javax.inject.Inject

class RetrieveListTransformersInteractor @Inject constructor(
    private val repository: ITransformerRepository
) : VoidUseCase<Result<TransformerList>> {
    override suspend fun invoke(): Result<TransformerList> {
        return repository.getAll()
    }
}