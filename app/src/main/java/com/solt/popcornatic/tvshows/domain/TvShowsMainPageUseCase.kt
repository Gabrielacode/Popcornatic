package com.solt.popcornatic.tvshows.domain

import com.solt.popcornatic.tvshows.data.remote.repository.PagedTvShowsRepository
import javax.inject.Inject

class TvShowsMainPageUseCase @Inject constructor(val pagedTvShowsRepository: PagedTvShowsRepository) {
    fun getTvShowsAiringToday() = pagedTvShowsRepository.getPagedAiringTodayTvShows()
    fun getTvShowsOnTheAir() = pagedTvShowsRepository.getPagedOnTheAirTvShows()
    fun getPopularTvShows() = pagedTvShowsRepository.getPagedPopularTvShows()
    fun getTopRatedTvShows() = pagedTvShowsRepository.getPagedTopRatedTvShows()
}