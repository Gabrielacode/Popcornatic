package com.solt.popcornatic.tvshows.domain

import com.solt.popcornatic.tvshows.data.remote.repository.PagedTvShowsRepository
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import javax.inject.Inject

class TvShowDetailPageUseCase @Inject constructor(val repository: TvShowsRepositoryImpl, val pagedRepository: PagedTvShowsRepository) {
     suspend fun getTvShowById(id:Int) = repository.getTvShowById(id)
     suspend fun getTvShowSeason(id:Int, seasonNumber:Int) = repository.getTvShowSeason(id,seasonNumber)
      fun getTvShowRecommendations(id: Int)= pagedRepository.getPagedRecommendedTvShows(id)
}