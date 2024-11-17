package com.solt.popcornatic

const val BASE_URL = "https://api.themoviedb.org/3/"
const val BASE_IMAGE_URL ="https://image.tmdb.org/t/p/"
const val POSTER_IMAGE_SIZE ="original"
const val BACKDROP_IMAGE_SIZE = "original"
const val MOVIES_PATH = "movie"
const val TV_SHOWS_PATH = "tv"
const val US_ENGLISH = "en-US"
const val MAX_PAGE_NUMBER = 500
const val MIN_PAGE_NUMBER = 1

enum class AppendToResponseOptions( var title: String) {
    VIDEOS("videos"),IMAGES("images"),CREDITS("credits")
}