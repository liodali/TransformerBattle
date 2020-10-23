package dali.hamza.domain.repository

import dali.hamza.domain.model.GameResult
import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer

interface IAppRepository {
    suspend fun retrieveToken():Result<String>
    suspend fun playMatch(transformer: List<Transformer>): Result<GameResult>
}