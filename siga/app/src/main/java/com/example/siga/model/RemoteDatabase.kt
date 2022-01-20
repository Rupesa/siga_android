package com.example.siga.model

import androidx.lifecycle.MutableLiveData
import com.example.siga.model.DAO.LocalPostsDatabase
import com.example.siga.model.entities.Post
import com.example.siga.model.entities.User

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

class RemoteDatabase private constructor(private val firestore: FirebaseFirestore) {
    fun addRemotePost(post: Post){
        val collection = firestore.collection("posts").document(post.ownerId).collection("userPosts")
        val task = collection.add(post)
        task.addOnSuccessListener {
           post.postId = it.id
        }
        task.addOnFailureListener {
            var message = it.message
            var i = 1 + 1
        }

    }
    private var posts : MutableLiveData<List<Post>>
        get() { return posts}
        set(value) {posts = value}

    fun getRemotePosts(user: User): MutableLiveData<List<Post>> {
        var postsList: MutableLiveData<List<Post>>
        var postsCollection = firestore.collection("posts")
            .document(user.id)
            .collection("userPosts")

        postsCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            var p = querySnapshot?.toObjects(Post::class.java)

            posts.postValue(p)
        }
        return posts
    }
    companion object{
        @Volatile private var instance: RemoteDatabase? = null
        // private val remoteDatabase = Firebase.database

        fun getInstance (firestore: FirebaseFirestore) =
            instance ?: synchronized(this){ // if instance is null (not instantiated)
                instance ?: RemoteDatabase(firestore).also { instance = it } //also, if already instantiated return it ()
            }
    }
}