package dali.hamza.core.networking

import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("allspark")
    fun getToken(): Response<String>
}