package com.example.siga.view

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.siga.R
import java.util.jar.Manifest
import androidx.core.app.ActivityCompat.startActivityForResult

import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory

import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File
import java.lang.Exception


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [chooseAdd.newInstance] factory method to
 * create an instance of this fragment.
 */
class chooseAdd : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewImage: ImageView
    private lateinit var filePhoto : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_add, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddEvent = view.findViewById(R.id.addEvent) as Button
        val btnAddPost = view.findViewById(R.id.addPost) as Button

        viewImage = view.findViewById(R.id.viewImage) as ImageView;



        btnAddEvent.setOnClickListener{
            Log.d("Button", "Pressed")

            val fragment: Fragment = addEventFragment.newInstance("1", "2" )
            val transaction =  parentFragmentManager.beginTransaction()
            if (transaction != null) {
                Log.d("Transaction", "dif null")

                transaction.replace(R.id.frame_content, fragment, "addEvent_fragment")
                transaction.commit();
            }
            true
        }
        btnAddPost.setOnClickListener{
            Log.d("Button", "Pressed")
            val fragment: Fragment = addPostFragment.newInstance("1", "2" )
            val transaction = parentFragmentManager.beginTransaction()
            if (transaction != null) {
                Log.d("Transaction", "dif null")

                transaction.replace(R.id.frame_content, fragment, "addEvent_fragment")
                transaction.commit();

            }}
        true

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                chooseAdd().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}