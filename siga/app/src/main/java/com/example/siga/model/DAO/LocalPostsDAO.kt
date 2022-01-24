package com.example.siga.model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.siga.model.entities.LocalPost

@Dao
interface LocalPostsDAO {
    @Query("SELECT * FROM post")
    fun getAllPosts()  : LiveData<List<LocalPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: ArrayList<LocalPost>)

    @Delete
    fun delete(post: LocalPost)

    @Insert
    fun save(post: LocalPost)
}
