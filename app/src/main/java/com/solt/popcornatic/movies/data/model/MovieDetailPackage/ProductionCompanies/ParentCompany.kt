package com.solt.popcornatic.movies.data.model.MovieDetailPackage.ProductionCompanies


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParentCompany(
    @SerialName("id")
    val id: Int?,
    @SerialName("logo_path")
    val logoPath: String?,
    @SerialName("name")
    val name: String?
)