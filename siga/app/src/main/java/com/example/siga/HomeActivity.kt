package com.example.siga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var userImageUrl: String
    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userDescription: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        userImageUrl = currentUser?.photoUrl.toString()
        userName = currentUser?.displayName.toString()
        userEmail = currentUser?.email.toString()

        /*
        fun newInstance(mySerializableData: Any, anotherData: Int) = ProfileFragment().apply {
            //bundleOf() is an exstension method from KTX https://developer.android.com/kotlin/ktx
            arguments = bundleOf(userName, userEmail)
        }
        */

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)


    }
}