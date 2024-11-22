package com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)