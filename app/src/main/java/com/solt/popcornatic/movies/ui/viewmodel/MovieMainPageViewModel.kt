package com.solt.popcornatic.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.solt.popcornatic.movies.domain.MovieMainPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieMainPageViewModel @Inject constructor(val mainPageUseCase:MovieMainPageUseCase):ViewModel() {
     val listOfTrendingMovies
          get() =mainPageUseCase.getTrendingMovies().cachedIn(viewModelScope)
     val listOfPopularMovies
          get() = mainPageUseCase.getPopularMovies().cachedIn(viewModelScope)
     val listOfUpcomingMovies
          get() = mainPageUseCase.getUpcomingMovies().cachedIn(viewModelScope)
     val listOfTopRatedMovies
          get() = mainPageUseCase.getTopRatedMovies().cachedIn(viewModelScope)
}