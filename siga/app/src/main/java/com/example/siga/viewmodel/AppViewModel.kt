package com.example.siga.viewmodel

import androidx.lifecycle.ViewModel
import com.example.siga.model.AppRepository
import com.example.siga.model.entities.Post
import com.example.siga.model.entities.User

class AppViewModel(private val repository: AppRepository) : ViewModel(){

    fun getLocalPosts() = repository.getLocalPosts()

    fun addLocalPost(post: Post) = repository.addLocalPost(post)

    fun getRemotePosts(user: User) = repository.getRemotePosts(user)

    fun addRemotePost(post: Post) = repository.addRemotePost(post)

}