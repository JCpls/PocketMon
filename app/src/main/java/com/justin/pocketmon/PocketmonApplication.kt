package com.justin.pocketmon

import android.app.Application
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.util.ServiceLocator
import kotlin.properties.Delegates

/* An application that lazily provides a repository. Note that this Service Locator pattern is
* used to simplify the sample. Consider a Dependency Injection framework.
*/

class PocketmonApplication : Application() {

    // Depends on the flavor,
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
