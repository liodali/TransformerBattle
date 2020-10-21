package dali.hamza.domain.repository

import dali.hamza.domain.model.Transformer

interface ITransformerRepository {
    suspend fun getAll(): List<Transformer>
    suspend fun insertTransformer(transformer: Transformer): String
    suspend fun update(transformer: Transformer)
    suspend fun delete(idTransformer: String)
}