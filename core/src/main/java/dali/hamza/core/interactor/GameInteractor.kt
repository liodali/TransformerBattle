package dali.hamza.core.interactor

import dali.hamza.domain.interactor.UseCase
import dali.hamza.domain.model.GameResult
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val repository: IAppRepository
) :UseCase<List<Transformer>,Flow<Result<GameResult>>> {
    override suspend fun invoke(parameter: List<Transformer>): Flow<Result<GameResult>> {
        return repository.playMatch(parameter) as Flow<Result<GameResult>>
    }
}