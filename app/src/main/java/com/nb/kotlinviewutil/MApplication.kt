package com.nb.kotlinviewutil

import android.app.Application
import com.nb.kotlinui.KotlinUi

/**
 * Created by NieBin on 18-9-13
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KotlinUi.context = this
    }
}
