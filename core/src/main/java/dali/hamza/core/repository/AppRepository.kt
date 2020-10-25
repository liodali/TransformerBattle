package dali.hamza.core.repository

import dali.hamza.core.networking.TransformerApi
import dali.hamza.core.utilities.SessionManager
import dali.hamza.domain.model.*
import dali.hamza.domain.repository.IAppRepository
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: TransformerApi,
    private val sessionManager: SessionManager
) : IAppRepository {
    override suspend fun retrieveToken(): Result<String> {
        if (sessionManager.getValue(SessionManager.tokenKey) == null) {
            val s = api.getToken().data()
            if (s is Success<String>) {
                sessionManager.setValue(SessionManager.tokenKey, s.data)
            } else {
                return Failure(AppError(Throwable("No token")))
            }

        }
        return Success(sessionManager.getValue(SessionManager.tokenKey) as String)
    }

    override suspend fun playMatch(transformer: List<Transformer>): Result<GameResult> {

        return Failure(AppError(Throwable(), 404))
    }
}