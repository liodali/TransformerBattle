package dali.hamza.domain.repository

import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer

interface ITransformerRepository {
    suspend fun getAll(): Result<List<Transformer>>
    suspend fun insertTransformer(transformer: Transformer): Result<String>
    suspend fun update(transformer: Transformer)
    suspend fun delete(idTransformer: String)
}