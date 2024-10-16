package com.solt.popcornatic.movies.data.api

interface MovieApi {
    suspend fun getTrendingMovies()
    suspend fun getUpcomingMovies()
    suspend fun getTopRatedMovies()
    suspend fun getPopularMovies()
    
}