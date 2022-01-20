package com.example.siga.viewmodel

import android.content.Context
import com.example.siga.model.AppRepository
import com.example.siga.model.DAO.LocalPostsDatabase
import com.example.siga.model.RemoteDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage

object InjectorUtils {
    fun provideViewModelFactory(context: Context): ViewModelFactory{
        val repository = AppRepository.getInstance(LocalPostsDatabase.getDatabase(context), RemoteDatabase.getInstance(FirebaseFirestore.getInstance()) )
        return ViewModelFactory(repository)


    }
}