package com.solt.popcornatic.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solt.popcornatic.movies.data.paging.source.MoviePagingSource
import javax.inject.Inject

class PagedMovieRepository @Inject constructor(val movieRepositoryImpl: MovieRepositoryImpl) {
    fun getPagedTrendingMovies()=
        Pager(PagingConfig(10)){
            MoviePagingSource(1,movieRepositoryImpl,MovieRepositoryImpl::getTrendingMovies)
        }.flow

    fun getPagedPopularMovies()=
        Pager(PagingConfig(10)){
            MoviePagingSource(1,movieRepositoryImpl,MovieRepositoryImpl::getPopularMovies)
        }.flow
    fun getPagedUpcomingMovies()=
        Pager(PagingConfig(10)){
            MoviePagingSource(1,movieRepositoryImpl,MovieRepositoryImpl::getUpcomingMovies)
        }.flow
    fun getPagedTopRatedMovies()=
        Pager(PagingConfig(10)){
            MoviePagingSource(1,movieRepositoryImpl,MovieRepositoryImpl::getTopRatedMovies)
        }.flow
    }
