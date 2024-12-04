package com.solt.popcornatic.user.ui.viewmodel

import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItem
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItsItems
import com.solt.popcornatic.user.data.local.files.AccessImage
import com.solt.popcornatic.user.data.local.sharedprefs.SharedPrefs
import com.solt.popcornatic.user.domain.FavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(val favouriteUseCase: FavouriteUseCase, val userSharedPreferences: SharedPrefs,val accessImage: AccessImage):ViewModel() {

    fun setUserName(name:String) = userSharedPreferences.setUserName(name)
    fun getUserName():String = userSharedPreferences.getUserName()

    fun addANewFavourite(name: String){
        viewModelScope.launch {
            val favourite = Favourite(0,name)
            favouriteUseCase.addFavourites(favourite)
        }
    }
    suspend  fun getListOfFavourites()=
        favouriteUseCase.getListOfFavourites()

    suspend fun setUserImage(inputStream :InputStream) :Boolean{

        return viewModelScope.async {
            accessImage.setUserImageFile(inputStream)
            true
        }.await()

    }
    suspend fun getUserImage () = accessImage.readUserImage()

}