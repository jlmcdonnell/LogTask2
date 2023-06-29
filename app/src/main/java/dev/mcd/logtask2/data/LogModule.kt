package dev.mcd.logtask2.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LogModule {

    @Provides
    @Singleton
    fun logCacheFile(@ApplicationContext context: Context): File {
        return File(context.filesDir, "log.json").apply {
            if (!exists()) {
                createNewFile()
            }
        }
    }
}