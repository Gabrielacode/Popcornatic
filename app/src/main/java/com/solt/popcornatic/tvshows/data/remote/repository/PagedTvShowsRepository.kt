package com.solt.popcornatic.tvshows.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solt.popcornatic.MIN_PAGE_NUMBER
import com.solt.popcornatic.tvshows.data.remote.paging.TvShowsPagingSource
import com.solt.popcornatic.tvshows.data.remote.paging.TvShowsRecommendationsPagingSource
import javax.inject.Inject

class PagedTvShowsRepository @Inject constructor(val tvShowsRepository
: TvShowsRepositoryImpl) {
    fun getPagedAiringTodayTvShows ()= Pager(PagingConfig(10)){
        TvShowsPagingSource(tvShowsRepository,TvShowsRepositoryImpl::getTvShowsAiringToday)
    }.flow
    fun getPagedOnTheAirTvShows ()= Pager(PagingConfig(10)){
        TvShowsPagingSource(tvShowsRepository,TvShowsRepositoryImpl::getTvShowsOnTheAir)
    }.flow
    fun getPagedPopularTvShows ()= Pager(PagingConfig(10)){
        TvShowsPagingSource(tvShowsRepository,TvShowsRepositoryImpl::getPopularTvShows)
    }.flow
    fun getPagedTopRatedTvShows ()= Pager(PagingConfig(10)){
        TvShowsPagingSource(tvShowsRepository,TvShowsRepositoryImpl::getTopRatedTvShows)
    }.flow

    fun getPagedRecommendedTvShows( tvShowId:Int) = Pager(PagingConfig(10)){
        TvShowsRecommendationsPagingSource(tvShowId = tvShowId, MIN_PAGE_NUMBER,tvShowsRepository)
    }.flow
}