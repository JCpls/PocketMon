package com.justin.pocketmon.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.R
import com.justin.pocketmon.databinding.DialogCommentBinding
import com.justin.pocketmon.databinding.DialogPlanTodoBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.plan.todo.PlanToDoDialogArgs
import com.justin.pocketmon.plan.todo.PlanToDoViewModel
import com.justin.pocketmon.util.Logger


class CommentDialog : AppCompatDialogFragment() {

    private val viewModel by viewModels<CommentViewModel> { getVmFactory(CommentDialogArgs.fromBundle(requireArguments()).commentKey)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogCommentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // add data from Dialog to PlanEdit page
        val db = FirebaseFirestore.getInstance()
        val document = db.collection("Article").document()

        binding.addCommentButton.setOnClickListener{


            val articledata = viewModel.addComment.value!!

            articledata.comment.add(binding.commentEdit.text.toString())
            Logger.i("articledata.comment = ${articledata.comment}")
//             plan.method = binding.planTodoEdit.text

            Logger.d("first check for data from PlanEditPage => $articledata")

            viewModel.addComment(articledata)
            Logger.d("再檢查從detailPage帶過來的資料 => $articledata")

            viewModel.navigateToPlanEditPage()

        }

        viewModel.navigateToDetailPage.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                    viewModel.onCommentToDetailNavigated()
                }
            }
        )


        return binding.root
    }
}