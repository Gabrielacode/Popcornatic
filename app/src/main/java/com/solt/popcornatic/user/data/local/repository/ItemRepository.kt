package com.solt.popcornatic.user.data.local.repository

import androidx.room.RoomDatabase
import com.solt.popcornatic.user.data.local.database.dto.ItemDao
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(val dao:ItemDao) {
    //When we are inserting an item we also need to insert the id of the favourite
     suspend fun addItem(item: Item,favourite:Favourite)  = dao.insertItemWithItsFavourite(item,favourite)


    suspend fun removeItem(item: Item,favourite:Favourite) = dao.deleteItemWithItsFavourite(item,favourite)

}