package com.example.siga.model

import com.example.siga.model.DAO.LocalPostsDatabase
import com.example.siga.model.entities.LocalPost
import com.example.siga.viewmodel.Event
import com.example.siga.viewmodel.Post


//Singleton
class AppRepository private constructor(private val postsDao: LocalPostsDatabase, private val remoteDB: RemoteDatabase) {

    //Remote Posts
    fun fetchRemotePosts() = remoteDB.fetchRemotePosts()

    fun addRemotePost(post: Post){
        remoteDB.addRemotePost(post)

    }
    fun remotePosts() = remoteDB.posts

    // Local Posts
    fun fetchLocalPosts() = postsDao.localPosts().getAllPosts()

    fun addLocalPost(post: LocalPost){
        postsDao.localPosts().save(post);

    }


    //Remote Events
    fun fetchRemoteEvents() = remoteDB.fetchRemoteEvents()

    fun addRemoteEvents(event: Event) = remoteDB.addRemoteEvent(event)

    fun remoteEvents() = remoteDB.events

    companion object{
        @Volatile private var instance: AppRepository? = null
       // private val remoteDatabase = Firebase.database

        fun getInstance (postsDao: LocalPostsDatabase, remoteDB: RemoteDatabase) =
            instance ?: synchronized(this){ // if instance is null (not instantiated)
                instance ?: AppRepository(postsDao, remoteDB).also { instance = it } //also, if already instantiated return it ()
            }
    }


}

