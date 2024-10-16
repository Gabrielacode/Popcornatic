package com.solt.popcornatic.movies.domain

import com.solt.popcornatic.movies.data.paging.source.MoviePagingSource
import com.solt.popcornatic.movies.data.repository.PagedMovieRepository
import javax.inject.Inject

class MovieMainPageUseCase @Inject constructor(val moviePagedRepo:PagedMovieRepository) {

   fun getTrendingMovies() = moviePagedRepo.getPagedTrendingMovies()
   fun getPopularMovies() = moviePagedRepo.getPagedPopularMovies()
   fun getUpcomingMovies()=moviePagedRepo.getPagedUpcomingMovies()
   fun getTopRatedMovies()=moviePagedRepo.getPagedTopRatedMovies()
}