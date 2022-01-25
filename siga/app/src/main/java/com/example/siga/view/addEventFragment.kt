package com.example.siga.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.siga.R
import com.example.siga.viewmodel.AppViewModel
import com.example.siga.viewmodel.Event
import com.example.siga.viewmodel.InjectorUtils
import java.io.File
import com.google.firebase.storage.UploadTask

import android.widget.Toast

import com.google.firebase.storage.StorageReference

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addEventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var viewImage: ImageView
    private lateinit var filePhoto : File
    private var imageAdded : Boolean = false
    private lateinit var ctx:Context
    private lateinit var homeActivity: HomeActivity

    private lateinit var mediaUrl: String

    private var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        homeActivity = activity as HomeActivity
        storageReference = FirebaseStorage.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        ctx = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSelectPhoto = view.findViewById(R.id.btnSelectPhoto) as Button

        viewImage = view.findViewById(R.id.viewImage) as ImageView;
        val addBtn = view.findViewById(R.id.buttonAdd) as Button;

        val factory = InjectorUtils.provideViewModelFactory(ctx)
        val viewModel: AppViewModel by viewModels {factory}

        btnSelectPhoto.setOnClickListener{
            showPictureDialog()

        }
        addBtn.setOnClickListener{

            var locationText = view.findViewById(R.id.locationInput) as EditText

            var descriptionText = view.findViewById(R.id.descriptionInput) as EditText

            if (locationText.text.toString() != "" && descriptionText.text.toString() != "" && imageAdded){
                Log.d("Successss", "olaaaaa")

                var event:Event = Event()
                event.location = locationText.text.toString()
                event.description = descriptionText.text.toString()
                event.lat = -8.659969
                event.lng = 40.631375
                event.username = homeActivity.userName
                event.mediaUrl = mediaUrl
                event.userUrl = homeActivity.userImageUrl

                Log.d("mediaUrl", mediaUrl)

                viewModel.addRemoteEvent(event)

                val fragment: Fragment = CameraFragment.newInstance("Event Added with success", "2" )

                val transaction =  parentFragmentManager.beginTransaction()
                if (transaction != null) {
                    Log.d("Transaction", "dif null")

                    transaction.replace(R.id.frame_content, fragment, "camera_fragment")
                    transaction.commit();
                }
                //viewModel.add
            }

        }
    }
    private fun uploadImage(filePath: Uri?) {
        Log.d("Upload Image", "lol")
        if(filePath != null){
            Log.d("Upload Image", "not null")
            val ref = storageReference?.child("posts_" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    mediaUrl = downloadUri.toString()
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{
            Toast.makeText(ctx, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun takePhotoFromCamera() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        filePhoto = getPhotoFile("tmp")
        val providerFile =
            activity?.let { FileProvider.getUriForFile(it,"com.example.siga.provider", filePhoto) }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)

        startActivityForResult(takePictureIntent, 1)
    }
    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    fun choosePhotoFromGallary() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"

            startActivityForResult(this, 2)
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(
            "Select photo from gallery",
            "Capture photo from camera"
        )
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> //                    choosePhotoFromGallary();
                    choosePhotoFromGallary()
                1 -> //                    takePhotoFromCamera();
                    takePhotoFromCamera()

            }
        }
        pictureDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 &&resultCode == Activity.RESULT_OK) {
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            viewImage.setImageURI(data?.data)
            viewImage.setImageBitmap(takenPhoto)
            imageAdded = true
            if (data != null) {
                Log.d("Data not null", "notttt")
                uploadImage(Uri.fromFile(filePhoto))
            }

        }else if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            viewImage.setImageURI(data?.data)
            imageAdded = true
            if (data != null) {
                uploadImage(Uri.fromFile(filePhoto))
            }

        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    imageAdded = false
                }
            }
    }
}