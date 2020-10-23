package dali.hamza.transformerwar.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dali.hamza.core.interactor.RetrieveListTransformersInteractor
import dali.hamza.core.networking.TransformerApi
import dali.hamza.core.repository.TransformerRepository
import dali.hamza.domain.repository.ITransformerRepository
import dali.hamza.core.Utilities.SessionManager
import dali.hamza.transformerwar.utilities.Utilities
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() = Utilities.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideTransformerApi(retrofit: Retrofit) = retrofit.create(TransformerApi::class.java)



/*
    @Provides
    fun provideTransformerRepository(repository: TransformerRepository): ITransformerRepository =
        repository


    @Provides
    fun provideRetrieveListTransformersInteractor(repository: ITransformerRepository): RetrieveListTransformersInteractor=
        RetrieveListTransformersInteractor(repository)

*/
}