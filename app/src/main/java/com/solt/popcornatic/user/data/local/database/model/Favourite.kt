package com.solt.popcornatic.user.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.Relation
import androidx.room.TypeConverter
import androidx.room.TypeConverters

//This is the list entity
@Entity
 data class Favourite(
    @PrimaryKey(autoGenerate = true)
     val favouriteId:Int,
     val name:String,
  )
@Entity
data class Item(
 @PrimaryKey
 val itemId:Int,
 val name:String,
 val posterPath:String,
 @TypeConverters(FavouriteTypeConverter::class)
 val type:Type
)

//One Favourite can have many movies or tv shows
//and one Item ccn belong to many Favourites

//We will create a joint table

@Entity(primaryKeys = ["favouriteId","itemId"])
data class FavouriteAndItem(
 @ColumnInfo("favouriteId")
 val favouriteId:Int,
 @ColumnInfo("itemId")
 val itemId:Int
)

data class FavouriteAndItsItems(
 @Embedded
 val favourite:Favourite,
 @Relation(parentColumn = "favouriteId", entityColumn = "itemId"
  , associateBy = Junction(FavouriteAndItem::class,"favouriteId","itemId"))
 val listOfItems:List<Item>
)

enum class Type(val title:String){
 MOVIE("Movie") ,TV_SHOW("Tv Show")
}


class FavouriteTypeConverter{
 @TypeConverter
 fun fromTypeToString(type:Type):String{
  return  type.title
 }
 @TypeConverter
 fun fromStringToType(string:String):Type{
  return Type.valueOf(string)
 }

}

