package com.solt.popcornatic.user.data.local.database.dto

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItem
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItsItems
import com.solt.popcornatic.user.data.local.database.model.Item
import javax.inject.Inject

@Dao
interface FavouriteDao {
    //Get all the favourites
    @Transaction
    @Query("SELECT * FROM Favourite")
      fun  getAllFavouritesandItsItems():PagingSource<Int,FavouriteAndItsItems>

      @Query("SELECT * FROM Favourite")
      suspend fun getListOfFavourites():List<Favourite>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Upsert
    suspend fun updateFavourite(favourite: Favourite)

}
@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item:Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemFavouriteJointEntity(entity:FavouriteAndItem)
    @Delete
    suspend fun deleteItemFavouriteJointEntity(entity:FavouriteAndItem)

    //Here we insert the item first then the cross ref entity
    @Transaction
     suspend fun insertItemWithItsFavourite(item: Item,favourite:Favourite){
         insertItem(item)
         insertItemFavouriteJointEntity(FavouriteAndItem(favourite.favouriteId,item.itemId))
     }
    //Here we delete the crossref entity b4 we delete the item
    @Transaction
    suspend fun deleteItemWithItsFavourite(item: Item,favourite:Favourite){
       deleteItemFavouriteJointEntity(FavouriteAndItem(favourite.favouriteId,item.itemId))
        deleteItem(item)
    }

}