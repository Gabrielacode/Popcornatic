package com.solt.popcornatic.searchanddiscover.data.remote.api

import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.GenreOption
import retrofit2.http.GET

interface DiscoverOptionsApi {
    @GET("genre/movie/list")
  suspend fun getMoviesGenreList():GenreOption

}