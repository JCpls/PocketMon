package com.justin.pocketmon

import android.app.Application
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.util.ServiceLocator
import kotlin.properties.Delegates

class PocketmonApplication : Application() {

    val repository: PocketmonRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: PocketmonApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true
}
