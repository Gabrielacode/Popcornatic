package com.solt.popcornatic.movies.domain

import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import com.solt.popcornatic.movies.data.repository.PagedMovieRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(val movieRepo:MovieRepositoryImpl,val pagedMovieRepository: PagedMovieRepository) {
     suspend fun getMovieDetailsById(movieId :Int) = movieRepo.getMovieDetailsById(movieId)
     fun getMovieRecommendationsbyId(movieId: Int) = pagedMovieRepository.getPagedRecommendedMovies(movieId)

}