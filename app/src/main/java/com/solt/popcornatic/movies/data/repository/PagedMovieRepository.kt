package com.solt.popcornatic.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solt.popcornatic.MIN_PAGE_NUMBER
import com.solt.popcornatic.movies.data.paging.source.MoviePagingSource
import com.solt.popcornatic.movies.data.paging.source.MovieRecommendationsPagingSource
import javax.inject.Inject

class PagedMovieRepository @Inject constructor(val movieRepositoryImpl: MovieRepositoryImpl) {
    fun getPagedTrendingMovies() =
        Pager(PagingConfig(10)) {
            MoviePagingSource(MIN_PAGE_NUMBER, movieRepositoryImpl, MovieRepositoryImpl::getTrendingMovies)
        }.flow

    fun getPagedPopularMovies() =
        Pager(PagingConfig(10)) {
            MoviePagingSource(MIN_PAGE_NUMBER, movieRepositoryImpl, MovieRepositoryImpl::getPopularMovies)
        }.flow

    fun getPagedUpcomingMovies() =
        Pager(PagingConfig(10)) {
            MoviePagingSource(MIN_PAGE_NUMBER, movieRepositoryImpl, MovieRepositoryImpl::getUpcomingMovies)
        }.flow

    fun getPagedTopRatedMovies() =
        Pager(PagingConfig(10)) {
            MoviePagingSource(MIN_PAGE_NUMBER, movieRepositoryImpl, MovieRepositoryImpl::getTopRatedMovies)
        }.flow

    fun getPagedRecommendedMovies(movieId: Int) =
        Pager(PagingConfig(10)) {
            MovieRecommendationsPagingSource(movieId, MIN_PAGE_NUMBER, movieRepositoryImpl)
        }.flow
}
