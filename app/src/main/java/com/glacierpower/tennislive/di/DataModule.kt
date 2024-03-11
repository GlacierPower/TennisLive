package com.glacierpower.tennislive.di

import android.content.Context
import com.glacierpower.tennislive.data.TennisApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        private const val HEADER_KEY_RAPID_API_HOST = "x-rapidapi-host"
        private const val HEADER_VALUE_RAPID_API_HOST = "flashlive-sports.p.rapidapi.com"
        private const val HEADER_KEY_RAPID_API_KEY = "x-rapidapi-key"
        private const val BASE_URL = "https://flashlive-sports.p.rapidapi.com/v1/"
        private const val API_KEY = "445ccae8f6msh2cbf90cd04031d2p1ab8a8jsn2baf87fbd252"

        @Provides
        fun provideOkkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            context: Context
        ): OkHttpClient {
            val httpCacheDirectory = File(context.dataDir, "http-cache")
            val cacheSize: Long = 10 * 1024 * 1024
            val cache = Cache(httpCacheDirectory, cacheSize)

            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor { chain ->
                    val originalRequest = chain.request()

                    val newRequestBuilder = originalRequest.newBuilder()
                        .header(HEADER_KEY_RAPID_API_HOST, HEADER_VALUE_RAPID_API_HOST)
                        .header(HEADER_KEY_RAPID_API_KEY, API_KEY)

                    val newRequest = newRequestBuilder.build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .cache(cache)
                .build()
        }

        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        fun provideTennisApiService(retrofit: Retrofit): TennisApiService {
            return retrofit.create(TennisApiService::class.java)
        }

    }
}