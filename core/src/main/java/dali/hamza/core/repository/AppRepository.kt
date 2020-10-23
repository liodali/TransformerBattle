package dali.hamza.core.repository

import dali.hamza.core.networking.AppApi
import dali.hamza.domain.model.*
import dali.hamza.domain.repository.IAppRepository
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: AppApi,
) : IAppRepository {
    override suspend fun retrieveToken(): Result<String> {
        return api.getToken().data()
    }

    override suspend fun playMatch(transformer: List<Transformer>): Result<GameResult> {

        return Failure(AppError(Throwable(), 404))
    }
}