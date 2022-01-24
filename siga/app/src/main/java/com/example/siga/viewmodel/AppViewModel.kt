package com.example.siga.viewmodel

import androidx.lifecycle.ViewModel
import com.example.siga.model.AppRepository
import com.example.siga.model.entities.LocalPost
import com.example.siga.model.entities.User

class AppViewModel(private val repository: AppRepository) : ViewModel(){

    //Remote Posts
    fun fetchRemotePosts(user: User) = repository.fetchRemotePosts()

    fun addRemotePost(post: Post) = repository.addRemotePost(post)

    fun remotePosts() = repository.remotePosts()


    //Local Posts
    fun fetchLocalPosts() = repository.fetchLocalPosts()

    fun addLocalPost(post: LocalPost) = repository.addLocalPost(post)


    //Remote Events
    fun fetchRemoteEvents() = repository.fetchRemoteEvents()

    fun remoteEvents() = repository.remoteEvents()


}