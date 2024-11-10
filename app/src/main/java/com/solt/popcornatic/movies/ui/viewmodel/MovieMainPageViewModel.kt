package com.solt.popcornatic.movies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.solt.popcornatic.movies.domain.MovieMainPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieMainPageViewModel @Inject constructor(val useCase:MovieMainPageUseCase):ViewModel() {
     val listOfTrendingMovies
          get() =useCase.getTrendingMovies().cachedIn(viewModelScope)
     val listOfPopularMovies
          get() = useCase.getPopularMovies().cachedIn(viewModelScope)
     val listOfUpcomingMovies
          get() = useCase.getUpcomingMovies().cachedIn(viewModelScope)
     val listOfTopRatedMovies
          get() = useCase.getTopRatedMovies().cachedIn(viewModelScope)
}