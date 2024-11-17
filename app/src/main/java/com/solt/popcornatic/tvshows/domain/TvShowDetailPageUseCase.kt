package com.solt.popcornatic.tvshows.domain

import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import javax.inject.Inject

class TvShowDetailPageUseCase @Inject constructor(val repository: TvShowsRepositoryImpl) {
     suspend fun getTvShowById(id:Int) = repository.getTvShowById(id)
}