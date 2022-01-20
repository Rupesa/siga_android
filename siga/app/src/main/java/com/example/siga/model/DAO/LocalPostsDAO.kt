package com.example.siga.model.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.siga.model.entities.Post

@Dao
interface LocalPostsDAO {
    @Query("SELECT * FROM post")
    fun getAllPosts()  : LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: ArrayList<Post>)

    @Delete
    fun delete(post: Post)

    @Insert
    fun save(post: Post)
}
