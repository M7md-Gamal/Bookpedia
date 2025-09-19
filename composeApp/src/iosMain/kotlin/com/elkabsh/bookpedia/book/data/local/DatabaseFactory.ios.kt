package com.elkabsh.bookpedia.book.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val dbfile = documentsDir()+"/${FavoriteBookDatabase.DATABASE_NAME}"
        return Room.databaseBuilder<FavoriteBookDatabase>(
            name = dbfile
        )

    }

    @OptIn(ExperimentalForeignApi::class)
    fun documentsDir(): String{
        val documentsDir = NSFileManager.defaultManager().URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask ,
            appropriateForURL = null, create = false,
            error = null

        )
        return requireNotNull(documentsDir?.path)
    }
}