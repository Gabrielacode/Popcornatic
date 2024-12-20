package com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Episode(
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("crew")
    val crew: List<Crew?>?,
    @SerialName("episode_number")
    val episodeNumber: Int?,
    @SerialName("episode_type")
    val episodeType: String? ="",
    @SerialName("guest_stars")
    val guestStars: List<GuestStar?>?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("production_code")
    val productionCode: String?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("season_number")
    val seasonNumber: Int?,
    @SerialName("show_id")
    val showId: Int? =0,
    @SerialName("still_path")
    val stillPath: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)