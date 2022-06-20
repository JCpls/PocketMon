package com.justin.pocketmon.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.justin.pocketmon.data.source.DefaultPocketmonRepository
import com.justin.pocketmon.data.source.PocketmonDataSource
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.data.source.local.PocketmonLocalDataSource
import com.justin.pocketmon.data.source.remote.PocketmonRemoteDataSource

/* A Service Locator for the [PublisherRepository].
*/
object ServiceLocator {

    @Volatile
    var repository: PocketmonRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): PocketmonRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPublisherRepository(context)
        }
    }

    private fun createPublisherRepository(context: Context): PocketmonRepository {
        return DefaultPocketmonRepository(
            PocketmonRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): PocketmonDataSource {
        return PocketmonLocalDataSource(context)
    }
}