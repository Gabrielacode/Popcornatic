package com.solt.popcornatic.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.JointClasses.RecommendedMovies
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.MovieDetailResult
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendations
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendationsResult
import com.solt.popcornatic.movies.domain.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailPageViewModel @Inject constructor(val useCase: MovieDetailsUseCase):ViewModel() {
    //Create a failure and success system
    lateinit var movieDetails : MovieDetailResult
    lateinit var movieRecommendations : Flow<PagingData<RecommendedMovies>>

     private var _movieDetailsStateFlow = MutableStateFlow<LoadOperation>(LoadOperation.Loading())
     var movieDetailsStateFlow = _movieDetailsStateFlow.asStateFlow()




     suspend fun getMovieDetails(movieId:Int){
         when (val result = useCase.getMovieDetailsById(movieId, emptyList())){
             is ApiResult.Failure.ApiFailure ->{
                 _movieDetailsStateFlow.value =  LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)}


             is ApiResult.Success<*> ->  {
                    val movieDetail =  result .data as MovieDetailResult
                    this.movieDetails = movieDetail
                    _movieDetailsStateFlow.value = LoadOperation.Success(movieDetail)

             }


             is ApiResult.Failure.NetworkFailure -> _movieDetailsStateFlow.value = LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
         }
         movieRecommendations = useCase.getMovieRecommendationsbyMovieId(movieId).cachedIn(viewModelScope)
}
sealed interface LoadOperation{
    class Loading:LoadOperation
    class Success<T>(val data :T):LoadOperation
    class Failure(val errorType:ErrorType):LoadOperation

    enum class ErrorType {
        NETWORK

    }
}}