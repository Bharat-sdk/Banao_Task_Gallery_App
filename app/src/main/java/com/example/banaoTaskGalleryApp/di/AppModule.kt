package com.example.banaoTaskGalleryApp.di

import android.content.Context
import com.example.banaoTaskGalleryApp.utils.network.NetworkResultCallAdapterFactory
import com.google.gson.Gson
import com.example.banaoTaskGalleryApp.utils.constants.EndPoints.DEVELOPMENT_BASE_URL
import com.example.banaoTaskGalleryApp.utils.constants.EndPoints.LIVE_BASE_URL
import com.example.banaoTaskGalleryApp.utils.constants.EndPoints.USE_DEVELOPMENT
import com.example.banaoTaskGalleryApp.utils.network.interceptors.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(  if(USE_DEVELOPMENT) DEVELOPMENT_BASE_URL else LIVE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()



}