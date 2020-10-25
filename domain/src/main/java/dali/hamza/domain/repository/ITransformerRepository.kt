package dali.hamza.domain.repository

import dali.hamza.domain.model.Result
import dali.hamza.domain.model.Transformer
import dali.hamza.domain.model.TransformerList

interface ITransformerRepository {
    suspend fun getAll(): Result<TransformerList>
    suspend fun insertTransformer(transformer: Transformer): Result<String>
    suspend fun update(transformer: Transformer)
    suspend fun delete(idTransformer: String)
}