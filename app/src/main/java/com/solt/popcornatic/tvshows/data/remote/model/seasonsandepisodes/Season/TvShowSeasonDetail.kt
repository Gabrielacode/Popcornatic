package com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowSeasonDetail(
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("episodes")
    val episodes: List<Episode?>?,
    @SerialName("_id")
    val _id: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("season_number")
    val seasonNumber: Int?,
    @SerialName("vote_average")
    val voteAverage: Double?
)