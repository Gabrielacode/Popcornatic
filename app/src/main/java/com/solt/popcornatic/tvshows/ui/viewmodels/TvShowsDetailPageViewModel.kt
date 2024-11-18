package com.solt.popcornatic.tvshows.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.movies.ui.viewmodel.MovieDetailPageViewModel
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.TvShowSeasonDetail
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import com.solt.popcornatic.tvshows.domain.TvShowDetailPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//This will be shared between the detail page and the season and episode page
//This will store the tvshowDetail
@HiltViewModel
class TvShowsDetailPageViewModel @Inject constructor(val useCase:TvShowDetailPageUseCase):ViewModel() {
    private var _tvShowsDetailsStateFlow = MutableStateFlow<LoadOperation>(
        LoadOperation.Loading())

    var tvShowDetailsStateFlow = _tvShowsDetailsStateFlow.asStateFlow()
    var tvShowId:Int? = 0
     lateinit var tvShowsDetails :TvShowDetailResult
     lateinit var listofSeasonDetails:List<TvShowSeasonDetail?>






    suspend fun getTvShow(tvShowId:Int){
        this.tvShowId = tvShowId
        _tvShowsDetailsStateFlow.value = LoadOperation.Loading()
        val result = useCase.getTvShowById(tvShowId)
        when(result){
            is ApiResult.Failure.ApiFailure -> _tvShowsDetailsStateFlow.value =LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
            is ApiResult.Failure.NetworkFailure -> _tvShowsDetailsStateFlow.value =LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
            is ApiResult.Success<*> ->{
                val data = result.data as TvShowDetailResult
                _tvShowsDetailsStateFlow.value = LoadOperation.Success(data)

            }
        }
    }
    suspend fun getTvShowSeason(tvShowId: Int,seasonNumber:Int):LoadOperation{
        val result = useCase.getTvShowSeason(tvShowId,seasonNumber)
       return  when(result){
                is ApiResult.Failure.ApiFailure ->  LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
            is ApiResult.Failure.NetworkFailure -> LoadOperation.Failure(LoadOperation.ErrorType.NETWORK)
            is ApiResult.Success<*> ->{
                val data = result.data as TvShowSeasonDetail
                 LoadOperation.Success(data)

            }
        }
    }

    sealed interface LoadOperation{
        class Loading:LoadOperation
        class Success<T>(val data:T):LoadOperation
        class Failure(val errorType:ErrorType):LoadOperation

        enum class ErrorType {
            NETWORK

        }
    }
}
