package dali.hamza.core.repository

import dali.hamza.core.utilities.SessionManager
import dali.hamza.core.networking.TransformerApi
import dali.hamza.domain.model.*
import dali.hamza.domain.repository.ITransformerRepository
import retrofit2.Response
import javax.inject.Inject

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (AppError) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(AppError(Throwable(message()), code())) }
}

fun <T : Any> Response<T>.data(): Result<T> {
    try {
        onSuccess {
            return Success(it)
        }
        onFailure { return Failure(it) }
        return Failure(AppError(Throwable("GENERAL_NETWORK_ERROR")))
    } catch (e: Exception) {
        return Failure(AppError(e.fillInStackTrace()))
    }
}


class TransformerRepository @Inject constructor(
    private val api: TransformerApi,
    private val session: SessionManager,
) :
    ITransformerRepository {
    override suspend fun getAll(): Result<TransformerList> {
        return try {
            val token: String = session.getValue(SessionManager.tokenKey) as String
            api.getTransformers("${SessionManager.bearer}${token}").data()


        } catch (e: Exception) {
            Failure(
                AppError(Throwable("${e.message}"), 400)
            )
        }

    }

    override suspend fun insertTransformer(transformer: Transformer): Result<String> {
        return try {
            val token: String = session.getValue(SessionManager.tokenKey) as String
            val result=api.addNewTransformers("${SessionManager.bearer}${token}",transformer).data()
            return if(result is Success<Transformer>){
                Success(result.data.id)
            }else{
                Failure(AppError(Throwable("error to create transformer")))
            }

        } catch (e: Exception) {
            Failure(
                AppError(Throwable("${e.message}"), 400)
            )
        }
    }

    override suspend fun update(transformer: Transformer) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(idTransformer: String) {
        TODO("Not yet implemented")
    }
}