package dali.hamza.core.networking

import dali.hamza.domain.model.Transformer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TransformerApi {


    @GET("transformers")
     fun getTransformers(@Header("Authorization")authorization:String):Response<List<Transformer>>

    @POST("transformers")
    fun addNewTransformers(@Header("Authorization")authorization:String,transformer:Transformer):Response<String>

}