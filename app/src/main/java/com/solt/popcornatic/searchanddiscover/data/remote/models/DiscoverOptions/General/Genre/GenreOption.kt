package com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre


import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreOption(
    @SerialName("genres")
    val genres: List<Genre>?
)