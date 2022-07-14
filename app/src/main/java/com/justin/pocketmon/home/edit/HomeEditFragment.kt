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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.justin.pocketmon.login.UserManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.NavigationDirections.Companion.navigateToHomeFragment
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.User
import com.justin.pocketmon.databinding.FragmentHomeEditBinding
import com.justin.pocketmon.util.Logger
import java.io.ByteArrayOutputStream
import java.io.File

class HomeEditFragment : Fragment() {

    var uri: Uri? = null
    var PICK_CONTACT_REQUEST = 1
    var REQUEST_CODE = 42
    var img1: ImageView? = null
    var img2: ImageView? = null
    val FILE_NAME = "photo.jpg"
    var photoFile: File? = null
    val viewModel = HomeEditViewModel()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //來自檔案
        if (requestCode == PICK_CONTACT_REQUEST) {
            uri = data?.data
            img1?.setImageURI(uri)
            Log.d("justin","看一下上傳拿到的uri是啥 -> $uri")

        }
        //來自相機
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){

            val takeImage = BitmapFactory.decodeFile(photoFile?.absolutePath)
            Log.d("justin","看一下拍照拿的的takeImage是啥 -> $takeImage")
            img1?.setImageBitmap(takeImage)

            uri = context?.let { getImageUri(it,takeImage) }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

        //Bitmap to Uri 為了傳firebase
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

        //取得暫存圖片檔案
    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    // [START storage_field_initialization]
        //storage = Firebase.storage
        // [END storage_field_initialization]

    }

    private fun setContentView(fragmentHomeEdit: Int) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = HomeEditViewModel()

        val binding = FragmentHomeEditBinding.inflate(inflater)

        //UserManager
        binding.homeEditData =  UserManager

        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Article").document()

        binding.buttonArticleAdd.setOnClickListener {
            val article = Articledata()
            val time = Timestamp.now()

//            Log.d("justin", "這個是add後的回傳值 -> ${document.id}")

            article.id = UserManager.user.name

            article.id = document.id
            article.uid = UserManager.user.id
            article.name = UserManager.user.name
            article.title = binding.textArticleTitle.text.toString()
            article.category = binding.textArticleDegree.text.toString()
            article.content = binding.textArticleContent.text.toString()
            article.createdTime = time
            article.image = uri.toString()

            Logger.d("HomeEditFragment UserManager.user.name = ${UserManager.user.name}")
            Logger.d("HomeEditFragment UserManager.user.name = ${UserManager.user.id}")



//          viewModel.checkAuthor(article)
            viewModel.addData(article)

            this.findNavController().navigate(NavigationDirections.navigateToHomeFragment())

        }


        //打開攝影機----------------------------------------------------------------------
        //打開相機按鈕
        binding.buttonCameraOpen.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = context?.let { it1 -> FileProvider.getUriForFile(it1,"com.justin.pocketmon.fileprovider",
                photoFile!!
            ) }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
//            if(takePictureIntent.resolveActivity(MainActivity().packageManager)!= null){
//            }
            startActivityForResult(takePictureIntent,REQUEST_CODE)
        }

        //圖片上傳-------------------------------------------------------------------------
        img1 = binding.textArticleImage01
//        img2 = binding.textArticleImage02

        var storageReference = FirebaseStorage.getInstance().getReference()


        //上傳照片按鈕
        binding.buttonUploadImage.setOnClickListener {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            viewModel.liveData.value = true
            startActivityForResult(intent, PICK_CONTACT_REQUEST)
        }

        var unusedFileName = 0L
        //變更照片按鈕

        viewModel.liveData.observe(viewLifecycleOwner){
            uploadImageToStorage()
        }

//        binding.buttonProceedImage.setOnClickListener {
//
//            //上傳圖片   應該要改去viewModel用coroutineScope.launch
//            val time = System.currentTimeMillis()
//            val picStorage = storageReference.child("image$time")
//            Log.d("justin", "點擊更換圖片1，看一下picStorage是啥 -> $picStorage")
//
//            uri?.let { it1 ->
//                picStorage.putFile(it1).addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d("justin", "上傳成功")
//                        picStorage.downloadUrl.addOnSuccessListener {
//                            Log.d("justin", "看一下uri ->$it ")
//                            Glide.with(this /* context */)
//                                .load(it)
//                                .into(img2!!)
//
//                            Log.d("justin", "成功更換圖片")
//                            if(unusedFileName == 0L){
//                                unusedFileName = time
//                                Log.d("justin", "沒有過去的圖片")
//                            }else{
//                                storageReference.child("image$unusedFileName").delete()
//                                unusedFileName = time
//                                Log.d("justin", "刪除上次張上傳的圖片")
//                            }
//                        }.addOnFailureListener {
//                            // Handle any errors
//                        }
//                    } else {
//                        Log.d("justin", "上傳失敗")
//                    }
//                }
//            }
//
//        }

        //輸入完成度的 seekbar
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
        //上傳圖片   應該要改去viewModel用coroutineScope.launch
        val time = System.currentTimeMillis()
        val picStorage = storageReference.child("image$time")
        Log.d("justin", "點擊更換圖片1，看一下picStorage是啥 -> $picStorage")

        uri?.let { it1 ->
            picStorage.putFile(it1).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("justin", "上傳成功")
                    picStorage.downloadUrl.addOnSuccessListener {
                        Log.d("justin", "看一下uri ->$it ")
                        Glide.with(this /* context */)
                            .load(it)
                            .into(img1!!)

                        Log.d("justin", "成功更換圖片")
                        if(unusedFileName == 0L){
                            unusedFileName = time
                            Log.d("justin", "沒有過去的圖片")
                        }else{
                            storageReference.child("image$unusedFileName").delete()
                            unusedFileName = time
                            Log.d("justin", "刪除上次張上傳的圖片")
                        }
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                } else {
                    Log.d("justin", "上傳失敗")
                }
            }
        }

    }


}