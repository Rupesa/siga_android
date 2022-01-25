package com.example.siga.model

import androidx.lifecycle.MutableLiveData
import com.example.siga.viewmodel.Event
import com.example.siga.viewmodel.Post

import com.google.firebase.firestore.*

class RemoteDatabase private constructor(private val firestore: FirebaseFirestore) {
    private var _events:MutableLiveData<ArrayList<Event>> = MutableLiveData<ArrayList<Event>>()
    private var _posts:MutableLiveData<ArrayList<Post>> = MutableLiveData<ArrayList<Post>>()

    fun addRemotePost(post: Post){
        val collection = firestore.collection("posts2")
        val task = collection.add(post)
        task.addOnSuccessListener {
           //post.postId = it.id
        }
        task.addOnFailureListener {
            var message = it.message
            var i = 1 + 1
        }

    }
    fun addRemoteEvent(event: Event){
        val collection = firestore.collection("events")
        val task = collection.add(event)
        task.addOnSuccessListener {
            //post.postId = it.id
        }
        task.addOnFailureListener {
            var message = it.message
            var i = 1 + 1
        }

    }

    fun fetchRemotePosts() {
        var postsCollection = firestore.collection("posts2")

        postsCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            var postsList = querySnapshot?.toObjects(Post::class.java)

            _posts.postValue(postsList as ArrayList<Post>?)
        }
    }

    fun fetchRemoteEvents(){
        var eventsCollection = firestore.collection("events")
            eventsCollection.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                var eventsList = querySnapshot?.toObjects(Event::class.java)
                _events.postValue(eventsList as ArrayList<Event>?)
            }

    }

    internal var events : MutableLiveData<ArrayList<Event>>
        get() { return _events}
        set(value) {_events = value}

    internal var posts : MutableLiveData<ArrayList<Post>>
        get() { return _posts}
        set(value) {_posts = value}

    companion object{
        @Volatile private var instance: RemoteDatabase? = null
        // private val remoteDatabase = Firebase.database

        fun getInstance (firestore: FirebaseFirestore) =
            instance ?: synchronized(this){ // if instance is null (not instantiated)
                instance ?: RemoteDatabase(firestore).also { instance = it } //also, if already instantiated return it ()
            }
    }
}