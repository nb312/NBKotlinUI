package com.nb.kotlinui.extension

import com.nb.kotlinui.KotlinUi

/**
 * Created by NieBin on 18-9-13
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */


val Int.id2Color: Int
    get() = KotlinUi.context.resources.getColor(this)
