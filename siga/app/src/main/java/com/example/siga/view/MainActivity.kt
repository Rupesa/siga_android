package com.example.siga.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.example.siga.R
import com.example.siga.model.entities.User
import com.example.siga.viewmodel.AppViewModel
import com.example.siga.viewmodel.InjectorUtils
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser


        initializeUI()

        Handler().postDelayed({
            if (user != null) {
                val dashboardIntent = Intent(this, HomeActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            } else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }

        }, 2000)


    }

    private fun initializeUI(){
        val factory = InjectorUtils.provideViewModelFactory(this)
        val viewModel: AppViewModel by viewModels {factory}
        //val viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        val user = User( "111312808224151821684" ,  "", "",  "", "",
       null, "")

        ////var posts : List<Post>
        //viewModel.getRemotePosts(user).observe(this, Observer {
            //posts->
        //})
    }

}
