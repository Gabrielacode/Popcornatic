package com.solt.popcornatic.searchanddiscover.data.remote.models

import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.MovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchTv.TvResult

data class SearchItem(
    val id :Int?,
    val title: String?,
    val posterPath:String?,
    val backdropPath:String?,
    val searchItemType: SearchItemType
)
fun MovieResult.toSearchItem():SearchItem = SearchItem(this.id,this.title,this.posterPath,this.backdropPath,SearchItemType.MOVIE)
fun TvResult.toSearchItem():SearchItem = SearchItem(this.id,this.name,this.posterPath,this.backdropPath,SearchItemType.TVSHOW)



enum class SearchItemType(val title:String){
    MOVIE("Movie"),TVSHOW("Tv Show")
}
