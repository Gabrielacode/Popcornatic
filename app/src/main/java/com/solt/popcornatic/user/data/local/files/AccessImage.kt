package com.solt.popcornatic.user.data.local.files

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import javax.inject.Inject

const val USER_IMAGE_FILE = "user_image_file.png"
class AccessImage  @Inject constructor(@ApplicationContext  val context: Context){

     suspend fun setUserImageFile(inputStream: InputStream){
         return withContext(Dispatchers.IO) {
             val imageFileOutputStream  = context.openFileOutput(USER_IMAGE_FILE,Context.MODE_PRIVATE)
             imageFileOutputStream.buffered().use {
                 val bytes = inputStream.readBytes()
                 it.write(bytes)
                 inputStream.close()
             }
         }
    }
     suspend fun readUserImage():Bitmap?{
         return  withContext(Dispatchers.IO){
             try{
             val imageFile = context.openFileInput(USER_IMAGE_FILE)
             BitmapFactory.decodeStream(imageFile)
         }catch (e :FileNotFoundException){
             null
         }
         }

    }

}