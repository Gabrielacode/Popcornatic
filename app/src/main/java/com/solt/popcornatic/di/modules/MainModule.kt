package com.solt.popcornatic.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.solt.popcornatic.BASE_URL
import com.solt.popcornatic.movies.data.api.MovieApiImpl
import com.solt.popcornatic.searchanddiscover.data.remote.api.DiscoverOptionsApi
import com.solt.popcornatic.searchanddiscover.data.remote.api.SearchAndDiscoverApi
import com.solt.popcornatic.tvshows.data.remote.api.TvShowsApiImpl
import com.solt.popcornatic.user.data.local.database.db.FavouriteDb
import com.solt.popcornatic.user.data.local.database.dto.FavouriteDao
import com.solt.popcornatic.user.data.local.database.dto.ItemDao
import com.solt.popcornatic.user.data.local.database.model.FavouriteTypeConverter
import com.solt.popcornatic.user.data.local.sharedprefs.USER_SHARED_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        val logging = Interceptor { chain ->
            val request = chain.request()
            Log.i("HttpRetro",request.toString()+request.headers.toString())
            Log.i("HttpRetro",request.body.toString())

            chain.proceed(request).also {
                Log.i("HttpRetro",it.toString()+it.headers.toString())
                Log.i("HttpRetro",it.body.toString())
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
    @Provides
    @Singleton
    fun providesSearchAndDiscoverApi(retrofit: Retrofit):SearchAndDiscoverApi{
        return retrofit.create(SearchAndDiscoverApi::class.java)
    }
    @Provides
    @Singleton
    fun providesSDiscoverOptionsApi(retrofit: Retrofit):DiscoverOptionsApi{
        return retrofit.create(DiscoverOptionsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFavouriteDb( @ApplicationContext context: Context):FavouriteDb{
        return Room.databaseBuilder(context,FavouriteDb::class.java,"favourite").build()
    }
    @Provides
    @Singleton
    fun providesFavouriteDao( db: FavouriteDb): FavouriteDao {
        return db.favouriteDao()
    }
    @Provides
    @Singleton
    fun providesItemDao( db: FavouriteDb): ItemDao {
        return db.itemDao()
    }
    @Provides
    @Singleton
    fun providesSharedPreferences( @ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_SHARED_PREFS,Context.MODE_PRIVATE)
    }



}