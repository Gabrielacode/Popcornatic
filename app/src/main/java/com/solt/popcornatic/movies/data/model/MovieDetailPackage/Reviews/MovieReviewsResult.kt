package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Reviews


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewsResult(
    @SerialName("author")
    val author: String?,
    @SerialName("author_details")
    val authorDetails: AuthorDetails?,
    @SerialName("content")
    val content: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("url")
    val url: String?
)