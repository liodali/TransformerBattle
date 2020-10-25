package dali.hamza.transformerwar.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dali.hamza.core.networking.TransformerApi
import dali.hamza.core.repository.AppRepository
import dali.hamza.core.repository.TransformerRepository
import dali.hamza.core.utilities.SessionManager
import dali.hamza.domain.repository.IAppRepository
import dali.hamza.domain.repository.ITransformerRepository
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

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String, gson: Gson): Retrofit =
        Retrofit.Builder()
            //.addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideTransformerApi(retrofit: Retrofit) = retrofit.create(TransformerApi::class.java)


    @Provides
    fun provideTransformerRepository(repository: TransformerRepository): ITransformerRepository =
        repository

    @Provides
    fun provideAppRepository(repository: AppRepository): IAppRepository =
        repository

    @Provides
    fun provideSessionManager(application: Application): SessionManager =
        SessionManager(application)


}