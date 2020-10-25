package dali.hamza.core.networking

import dali.hamza.domain.model.Transformer
import retrofit2.Response
import retrofit2.http.*

interface TransformerApi {


    @Headers(
        "Content-Type: text/html; charset=utf-8",
        "Accept:text/html",
    )
    @GET("allspark")
    suspend fun getToken(): Response<String>

    @GET("transformers")
    suspend fun getTransformers(@Header("Authorization") authorization: String): Response<List<Transformer>>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("transformers")
    suspend fun addNewTransformers(
        @Header("Authorization") authorization: String,
        @Body transformer: Transformer
    ): Response<Transformer>

}