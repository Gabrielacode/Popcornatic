package com.solt.popcornatic.movies.data.model.MovieDetailPackage.JointClasses

import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendationsResult
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar.MovieSimilarResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//This class will be  a class that will contain both the movie recommendations and movie similar from the TMDB


data class RecommendedMovies(

    val id: Int,

    val posterPath: String?,

    val backdropPath: String?,
 )




