package com.solt.popcornatic.user.data.local.repository

import androidx.paging.PagingSource
import com.solt.popcornatic.user.data.local.database.dto.FavouriteDao
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItsItems
import javax.inject.Inject

class FavouriteRepository @Inject constructor(val dao:FavouriteDao) {
  fun  getListOfFavourites():PagingSource<Int,FavouriteAndItsItems> = dao.getAllFavouritesandItsItems()
  suspend fun getFavourites():List<Favourite> = dao.getListOfFavourites()
    suspend fun  addFavourites(favourite: Favourite) = dao.insertFavourite(favourite)
    suspend fun updateFavourites(favourite: Favourite) = dao.updateFavourite(favourite)
    suspend fun  deleteFavourites(favourite: Favourite) = dao.deleteFavourite(favourite)

}