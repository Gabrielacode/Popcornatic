package com.solt.popcornatic.di.modules

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solt.popcornatic.BASE_URL
import com.solt.popcornatic.movies.data.api.MovieApiImpl
import com.solt.popcornatic.tvshows.data.remote.api.TvShowsApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 class MainModule {
    private var json = Json { this.ignoreUnknownKeys = true }

    @Provides
    @Singleton
     fun providesOkHttp():OkHttpClient{
         val accessToken ="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlMDBmYWQ0NWVkZmJlN2IzOTY5ZGI5MTVlOGY1MmRlYiIsIm5iZiI6MTcyODY3ODUzMC4wMjA0NDgsInN1YiI6IjY2ZGIxNWM1NTFiZjZhYjk0MDcwNDJjMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.H75BZRntCvbKh-L9KUqvAxPfnolKlVpVIFmE_TQsAoA";
        val logging = object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                Log.i("HttpRetro",request.toString()+request.headers.toString())
                Log.i("HttpRetro",request.body.toString())

                return chain.proceed(request).also {
                    Log.i("HttpRetro",it.toString()+it.headers.toString())
                    Log.i("HttpRetro",it.body.toString())
                }
            }

        }
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val apiKeyAuthorizationInterceptor = object:Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()

               val newRequest =  request.newBuilder().addHeader("Authorization"," Bearer "+accessToken).build()
                return chain.proceed(newRequest)
            }


        }
     return OkHttpClient.Builder()
         .addInterceptor(apiKeyAuthorizationInterceptor)
         .addInterceptor(httpLoggingInterceptor)
            .build()


     }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient):Retrofit{
        val jsonConverter = json.asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .addConverterFactory(jsonConverter)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    }
    @Provides
    @Singleton
     fun providesMoviesApi(retrofit: Retrofit):MovieApiImpl{
         return retrofit.create(MovieApiImpl::class.java)
     }
    @Provides
    @Singleton
    fun providesTvShowsApi(retrofit: Retrofit):TvShowsApiImpl{
        return retrofit.create(TvShowsApiImpl::class.java)
    }

}