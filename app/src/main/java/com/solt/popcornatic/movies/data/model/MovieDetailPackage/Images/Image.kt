package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images

import android.util.Log

data class Image(val filePath:String,val voteCount:Int,val voteAvg:Int)

fun Backdrop.toImage():Image = Image(this.filePath?:"",this.voteCount?:0,this.voteCount?:0)
fun Poster.toImage():Image = Image(this.filePath?:"",this.voteCount?:0,this.voteCount?:0)
fun Logo.toImage():Image = Image(this.filePath?:"",this.voteCount?:0,this.voteCount?:0)


