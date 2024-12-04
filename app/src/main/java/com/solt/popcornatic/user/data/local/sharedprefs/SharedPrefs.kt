package com.solt.popcornatic.user.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

const val USER_SHARED_PREFS = "user_profile"
const val USER_NAME ="user_name"
class SharedPrefs @Inject constructor(val appSharedPrefs: SharedPreferences) {

    fun getUserName():String{
      return  appSharedPrefs.getString(USER_NAME,"Name not Set")?:"Name not Set"
    }
     fun setUserName(name:String){
         appSharedPrefs.edit().also {
             it.putString(USER_NAME,name)
             it.apply()
         }
     }
}