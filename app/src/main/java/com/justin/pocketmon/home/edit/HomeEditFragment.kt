package com.justin.pocketmon.home.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.justin.pocketmon.login.UserManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.data.ArticleData
import com.justin.pocketmon.databinding.FragmentHomeEditBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.util.Logger
import java.io.ByteArrayOutputStream
import java.io.File

class HomeEditFragment : Fragment() {

    private val viewModel by viewModels<HomeEditViewModel> { getVmFactory()}

    var uri: Uri? = null
    var PICK_CONTACT_REQUEST = 1
    var REQUEST_CODE = 42
    var image: ImageView? = null
    val FILE_NAME = "photo.jpg"
    var photoFile: File? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // from album pic
        if (requestCode == PICK_CONTACT_REQUEST) {
            uri = data?.data
            image?.setImageURI(uri)
            Logger.i("take a look at album pic uri -> $uri ")
        }

        // from camera
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){

            val cameraImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
            Logger.i("take a look at camera uri -> $cameraImage")

            image?.setImageBitmap(cameraImage)
            uri = context?.let { getImageUri(it,cameraImage) }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

        // Bitmap to Uri
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

        // get pic from storage
    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        // UserManager
        binding.homeEditData =  UserManager

        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Article").document()

        binding.buttonArticleAdd.setOnClickListener {

            val articledata = ArticleData()
            val time = Timestamp.now()

            articledata.id = document.id
            articledata.uid = UserManager.user.id
            articledata.name = UserManager.user.name
            articledata.title = binding.textArticleTitle.text.toString()
            articledata.category = binding.textArticleDegree.text.toString()
            articledata.content = binding.textArticleContent.text.toString()
            articledata.createdTime = time
            articledata.image = uri.toString()

            Logger.d("HomeEditFragment UserManager.user.name = ${UserManager.user.name}")
            Logger.d("HomeEditFragment UserManager.user.id = ${UserManager.user.id}")

            viewModel.pushArticle(articledata)

            this.findNavController().navigate(NavigationDirections.navigateToHomeFragment())

        }


        binding.buttonCameraOpen.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = context?.let { it1 -> FileProvider.getUriForFile(it1,"com.justin.pocketmon.fileprovider",
                photoFile!!
            ) }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            startActivityForResult(takePictureIntent,REQUEST_CODE)
        }

        image = binding.textArticleImage01

        // upload the pic
        binding.buttonUploadImage.setOnClickListener {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            viewModel.liveData.value = true
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }


        viewModel.liveData.observe(viewLifecycleOwner){
            uploadImageToStorage()
        }

        // seekbar for degree of completion
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, process: Int, fromUser: Boolean) {
                binding.textArticleDegree.text = process.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        return binding.root
    }

    private fun uploadImageToStorage() {

        var unusedFileName = 0L
        var storageReference = FirebaseStorage.getInstance().getReference()
        val time = System.currentTimeMillis()
        val picStorage = storageReference.child("image$time")
        Logger.i("take a look at picStorage -> $picStorage ")

        uri?.let { it1 ->
            picStorage.putFile(it1).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("successfully uploaded")
                    picStorage.downloadUrl.addOnSuccessListener {
                        Logger.i("take a look at uri ->$it ")
                        Glide.with(this /* context */)
                            .load(it)
                            .into(image!!)

                        if(unusedFileName == 0L){
                            unusedFileName = time

                        }else{
                            storageReference.child("image$unusedFileName").delete()
                            unusedFileName = time
                        }
                    }.addOnFailureListener {
                    }
                } else {
                    Logger.i("fail to upload")
                }
            }
        }
    }
}