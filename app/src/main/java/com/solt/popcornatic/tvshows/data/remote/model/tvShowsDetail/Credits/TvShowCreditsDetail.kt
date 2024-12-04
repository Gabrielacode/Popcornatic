package com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowCreditsDetail(
    @SerialName("cast")
    val cast: List<Cast>?,
    @SerialName("crew")
    val crew: List<Crew>?,
    @SerialName("id")
    val id: Int?
)