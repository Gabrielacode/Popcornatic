package com.solt.popcornatic

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class PopcornaticApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}