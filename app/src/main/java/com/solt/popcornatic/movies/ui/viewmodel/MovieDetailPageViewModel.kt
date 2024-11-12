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
import kotlinx.coroutines.flow.retry
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailPageViewModel @Inject constructor(val useCase: MovieDetailsUseCase):ViewModel() {
    //Create a failure and success system

    lateinit var movieRecommendations : Flow<PagingData<RecommendedMovies>>

     private var _movieDetailsStateFlow = MutableStateFlow<LoadOperation>(LoadOperation.Loading())
     var movieDetailsStateFlow = _movieDetailsStateFlow.asStateFlow()




     suspend fun getMovieDetails(movieId:Int){

         val result = useCase.getMovieDetailsById(movieId, emptyList())
         movieRecommendations = useCase.getMovieRecommendationsbyMovieId(movieId).cachedIn(viewModelScope)
         when (result){
             is ApiResult.Failure.ApiFailure ->{
                 _movieDetailsStateFlow.value =  LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)}


             is ApiResult.Success<*> ->  {
                    val movieDetail =  result .data as MovieDetailResult

                    _movieDetailsStateFlow.value = LoadOperation.Success(movieDetail)

             }


             is ApiResult.Failure.NetworkFailure -> _movieDetailsStateFlow.value = LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
         }

}
sealed interface LoadOperation{
    class Loading:LoadOperation
    class Success<T>(val data :T):LoadOperation
    class Failure(val errorType:ErrorType):LoadOperation

    enum class ErrorType {
        NETWORK

    }
}}