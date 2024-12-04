package com.solt.popcornatic.user.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItsItems
import com.solt.popcornatic.user.data.local.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteUseCase @Inject constructor(val repository: FavouriteRepository) {
    suspend fun  getListOfFavourites():Flow<PagingData<FavouriteAndItsItems>> =
        Pager(PagingConfig(10)){repository.getListOfFavourites()} .flow
    suspend fun getAllFavourites() = repository.getFavourites()
    suspend fun  addFavourites(favourite: Favourite) = repository.addFavourites(favourite)
    suspend fun updateFavourites(favourite: Favourite) = repository.updateFavourites(favourite)
    suspend fun  deleteFavourites(favourite: Favourite) = repository.deleteFavourites(favourite)
}