package com.solt.popcornatic.user.data.local.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solt.popcornatic.user.data.local.database.dto.FavouriteDao
import com.solt.popcornatic.user.data.local.database.dto.ItemDao
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItem
import com.solt.popcornatic.user.data.local.database.model.Item

@Database([Favourite::class, Item::class, FavouriteAndItem::class], version = 1)
abstract class FavouriteDb :RoomDatabase(){
    abstract fun favouriteDao():FavouriteDao
    abstract fun itemDao():ItemDao
}