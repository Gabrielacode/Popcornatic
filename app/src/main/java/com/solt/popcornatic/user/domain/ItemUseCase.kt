package com.solt.popcornatic.user.domain

import com.solt.popcornatic.movies.data.model.MovieDetailPackage.MovieDetailResult
import com.solt.popcornatic.movies.ui.fragments.TYPE
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TvShowResult
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.Item
import com.solt.popcornatic.user.data.local.database.model.Type
import com.solt.popcornatic.user.data.local.repository.ItemRepository
import javax.inject.Inject

class ItemUseCase @Inject constructor(val repository: ItemRepository) {
   suspend fun addMovieItem(movie:MovieDetailResult,favourite: Favourite){
        val item = Item(movie.id!!,movie.title?:"No Name",movie.posterPath?:movie.backdropPath!!,Type.MOVIE )
        repository.addItem(item,favourite)
    }
    suspend fun addTvShowItem(tvShow:TvShowDetailResult,favourite: Favourite){
        val item = Item(tvShow.id!!,tvShow.name?:"No Name",tvShow.posterPath?:tvShow.backdropPath!!,Type.TV_SHOW)
        repository.addItem(item,favourite)
    }
    suspend fun addItem(item: Item,favourite: Favourite)= repository.addItem(item,favourite)
}