package com.solt.popcornatic.searchanddiscover.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.Genre
import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import com.solt.popcornatic.searchanddiscover.domain.DiscoverUseCase
import com.solt.popcornatic.searchanddiscover.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDiscoverViewModel @Inject constructor(val useCase: SearchUseCase,val discoverUseCase: DiscoverUseCase):ViewModel() {
    var searchResultsType = SearchResultsType.BOTH
        set(value){
            field = value
            searchResultsTypeStateFlow.value = value
        }
    val searchResultsTypeStateFlow = MutableStateFlow(searchResultsType)

    var listofMovieDiscoverOptions =  mutableMapOf<DiscoverOptions.Options.MovieOptions,DiscoverOptions.MovieDiscoverOptions>()
    val movieOptionsStateFlow = MutableStateFlow<Map<DiscoverOptions.Options.MovieOptions,DiscoverOptions.MovieDiscoverOptions>>(
        emptyMap()
    )
    var listofTvDiscoverOptions = mutableMapOf<DiscoverOptions.Options.MovieOptions,DiscoverOptions.TvDiscoverOptions>()

    val tvOptionsStateFlow = MutableStateFlow<Map<DiscoverOptions.Options.MovieOptions,DiscoverOptions.TvDiscoverOptions>>(
        emptyMap()
    )
    var state  = State.SEARCH
        set(value)
            {   field = value
                stateFlow.value = value
            }
    //This will send flow to UI indicating the state
    val stateFlow = MutableStateFlow(state)

    fun search(query: String) = useCase.search(query,searchResultsType).cachedIn(viewModelScope)
    fun discover(options:Map< out DiscoverOptions.Options, DiscoverOptions>)= discoverUseCase.discover(searchResultsType,options).cachedIn(viewModelScope)

    fun setState(){
        when(state){
            State.DISCOVER -> state = State.SEARCH
            State.SEARCH -> state = State.DISCOVER
        }
    }

    fun addMovieDiscoverOption(key :DiscoverOptions.Options.MovieOptions, option:DiscoverOptions.MovieDiscoverOptions){
        listofMovieDiscoverOptions[key] = option
        val newMap = mutableMapOf<DiscoverOptions.Options.MovieOptions,DiscoverOptions.MovieDiscoverOptions>()
        newMap.putAll(listofMovieDiscoverOptions)
        movieOptionsStateFlow.value = newMap
    }

    fun removeMovieDiscoverOption(key: DiscoverOptions.Options.MovieOptions){
        listofMovieDiscoverOptions.remove(key)
        val newMap = mutableMapOf<DiscoverOptions.Options.MovieOptions,DiscoverOptions.MovieDiscoverOptions>()
        newMap.putAll(listofMovieDiscoverOptions)
        movieOptionsStateFlow.value = newMap

    }
//    fun addTvDiscoverOption(option: DiscoverOptions.TvDiscoverOptions){
//        listofTvDiscoverOptions.value += option
//    }
//    fun removeTvDiscoverOption(option: DiscoverOptions.TvDiscoverOptions){
//        listofTvDiscoverOptions.value.remove(option)
//        viewModelScope.launch {
//            listofTvDiscoverOptions.emit(listofTvDiscoverOptions.value)
//        }
//    }

    //The viewmodel will also listen for events from the both list

}
enum class State{
    DISCOVER,SEARCH
}
sealed interface DiscoverOptions{
    //What will be the passed to the api as a part of the Query Map
    fun toQuery():String
    //What will be displayed to the user
    fun toDisplay():String
    sealed interface Options{
        fun toQuery():String
        enum class MovieOptions(val queryName: String,val title:String):Options{
            GENRES("with_genres","Genre") {
                override fun toQuery(): String {
                    return queryName
                }
            }
        }
    }
    sealed class MovieDiscoverOptions :DiscoverOptions{


      data class ByGenres(val list:List<Genre>):MovieDiscoverOptions()
      {
          override fun toQuery():String{
              return list.joinToString(separator = ",") { it.id.toString() }
          }

          override fun toDisplay(): String ="Genres : ${list.joinToString { it.name.toString() }}"




      }
    }
    sealed class TvDiscoverOptions:DiscoverOptions{}
}