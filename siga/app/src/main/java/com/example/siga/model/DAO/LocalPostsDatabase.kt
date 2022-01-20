package com.example.siga.model.DAO
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.siga.model.AppRepository
import com.example.siga.model.entities.Post

@Database(entities=arrayOf(Post::class), version = 1)
abstract class LocalPostsDatabase : RoomDatabase() {
    abstract fun localPosts() : LocalPostsDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocalPostsDatabase? = null


        fun getDatabase(context: Context): LocalPostsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalPostsDatabase::class.java,
                    "post_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}