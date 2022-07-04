package com.justin.pocketmon.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.MainActivity
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.databinding.FragmentHomeEditBinding
import com.justin.pocketmon.databinding.FragmentIntroBinding
import com.justin.pocketmon.home.edit.HomeEditViewModel

class IntroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val viewModel = HomeEditViewModel()

        val binding = FragmentIntroBinding.inflate(inflater)
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Article").document()

        binding.introRocketIcon.setOnClickListener {

            (activity as MainActivity).navigateToProfile()

        }

        return binding.root
    }


}