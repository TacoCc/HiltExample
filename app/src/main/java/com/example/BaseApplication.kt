package com.example

import android.app.Application
import com.hjq.toast.Toaster
import dagger.hilt.android.HiltAndroidApp

/**
 * Desc:
 * @author lijt
 * Created on 2022/10/9 9:56
 * Email: lijt@eetrust.com
 */
@HiltAndroidApp
class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Toaster.init(this)
    }
}