package dali.hamza.core.networking

import dali.hamza.domain.model.Transformer
import dali.hamza.domain.model.TransformerList
import retrofit2.Response
import retrofit2.http.*

interface TransformerApi {


    @Headers(
        "Content-Type: text/html; charset=utf-8",
        "Accept:text/html",
    )
    @GET("allspark")
    suspend fun getToken(): Response<String>

    @Headers(
        "Content-Type: application/json"
    )
    @GET("transformers")
    suspend fun getTransformers(@Header("Authorization") authorization: String): Response<TransformerList>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("transformers")
    suspend fun addNewTransformers(
        @Header("Authorization") authorization: String,
        @Body transformer: Transformer
    ): Response<Transformer>

    @DELETE("transformers/{id}")
    suspend fun deleteTransformer(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<*>

    @Headers(
        "Content-Type: application/json"
    )
    @PUT("transformers")
    suspend fun modifyTransformer(
        @Header("Authorization") authorization: String,
        @Body transformer: Transformer,
    ): Response<Transformer>

}