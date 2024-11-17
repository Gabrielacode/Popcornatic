package com.solt.popcornatic.tvshows.ui.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.solt.popcornatic.tvshows.domain.TvShowsMainPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsMainPageViewModel @Inject constructor(val useCase :TvShowsMainPageUseCase):ViewModel() {
    val listOfTvShowsAiringToday
        get() = useCase.getTvShowsAiringToday()
    val listOfTvShowsOnTheAir
        get() = useCase.getTvShowsOnTheAir()
    val listOfPopularTvShows
        get() = useCase.getPopularTvShows()
    val listOfTopRatedTvShows
        get() = useCase.getTopRatedTvShows()
}