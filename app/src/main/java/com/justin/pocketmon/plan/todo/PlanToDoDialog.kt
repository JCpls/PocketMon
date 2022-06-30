package com.justin.pocketmon.plan.todo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.ToDo
import com.justin.pocketmon.databinding.DialogPlanTodoBinding
import com.justin.pocketmon.detail.DetailFragmentArgs
import com.justin.pocketmon.detail.DetailViewModel
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.home.edit.HomeEditViewModel
import com.justin.pocketmon.plan.PlanViewModel
import com.justin.pocketmon.plan.edit.PlanEditFragmentArgs
import com.justin.pocketmon.util.ServiceLocator.repository
import java.util.stream.Collectors.toList


class PlanToDoDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<PlanToDoViewModel> { getVmFactory(PlanToDoDialogArgs.fromBundle(requireArguments()).todoKey)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogPlanTodoBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // add data from Dialog to PlanEdit page
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Plans").document()

        binding.planTodoButton.setOnClickListener{


            val plan = viewModel.addedTodo.value!!
            plan.method =  binding.planTodoEdit.text.toString()

            Log.d("justin","first check for data from PlanEditPage => $plan")

            viewModel.addToDo(plan)
            Log.d("justin","再檢查從planEdit帶過來的資料 => $plan")


        }

        return binding.root
    }
}
