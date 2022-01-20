package com.example.siga.model

import com.example.siga.model.DAO.LocalPostsDatabase
import com.example.siga.model.entities.Post
import com.example.siga.model.entities.User


//Singleton
class AppRepository private constructor(private val postsDao: LocalPostsDatabase, private val remoteDB: RemoteDatabase) {

    fun addLocalPost(post: Post){
        postsDao.localPosts().save(post);

    }

    fun getLocalPosts() = postsDao.localPosts().getAllPosts()

    fun addRemotePost(post: Post){
        remoteDB.addRemotePost(post)

    }
    fun getRemotePosts(user: User) = remoteDB.getRemotePosts(user)

    companion object{
        @Volatile private var instance: AppRepository? = null
       // private val remoteDatabase = Firebase.database

        fun getInstance (postsDao: LocalPostsDatabase, remoteDB: RemoteDatabase) =
            instance ?: synchronized(this){ // if instance is null (not instantiated)
                instance ?: AppRepository(postsDao, remoteDB).also { instance = it } //also, if already instantiated return it ()
            }
    }


}

