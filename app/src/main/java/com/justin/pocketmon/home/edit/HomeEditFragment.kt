package com.justin.pocketmon.home.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.NavigationDirections.Companion.navigateToHomeFragment
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.databinding.FragmentHomeEditBinding

class HomeEditFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [START storage_field_initialization]
        //storage = Firebase.storage
        // [END storage_field_initialization]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = HomeEditViewModel()

        val binding = FragmentHomeEditBinding.inflate(inflater)
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Article").document()

        binding.buttonArticleAdd.setOnClickListener {
            val article = Articledata()
            val time = Timestamp.now()

//            Log.d("justin", "這個是add後的回傳值 -> ${document.id}")
            article.uid = document.id
            article.title = binding.textArticleTitle.text.toString()
            article.category = binding.textArticleCategory.text.toString()
            article.content = binding.textArticleContent.text.toString()
            article.createdTime = time

//          viewModel.checkAuthor(article)
            viewModel.addData(article)

            this.findNavController().navigate(NavigationDirections.navigateToHomeFragment())

        }


        return binding.root
    }


}