package com.example.siga.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.siga.R
import com.example.siga.view.HomeActivity
import com.example.siga.view.LoginActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var ivProfile: ImageView
private lateinit var tvName: TextView
private lateinit var tvEmail: TextView
private lateinit var btnLogout: Button

private lateinit var homeActivity: HomeActivity

private lateinit var imageUrl: String
private lateinit var name: String
private lateinit var email: String
private lateinit var description: String


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        homeActivity = activity as HomeActivity
        imageUrl = homeActivity.userImageUrl
        name = homeActivity.userName
        email = homeActivity.userEmail
        description = ""

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ivProfile = view.findViewById(R.id.ivProfile) as ImageView
        tvName = view.findViewById(R.id.tvName) as TextView
        tvEmail = view.findViewById(R.id.tvEmail) as TextView
        btnLogout = view.findViewById(R.id.logout_btn) as Button

        Glide.with(this).load(imageUrl).into(ivProfile);
        tvName.text = name
        tvEmail.text = email

        btnLogout.setOnClickListener {
            homeActivity.mAuth.signOut()
            homeActivity.mGoogleSignInClient.signOut();
            val intent = Intent(homeActivity, LoginActivity::class.java)
            startActivity(intent)
            homeActivity.finish()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}