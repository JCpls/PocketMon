package com.justin.pocketmon.comment

import android.os.Bundle
import android.util.Log
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
import com.justin.pocketmon.data.Broadcast
import com.justin.pocketmon.data.Comment
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.databinding.DialogCommentBinding
import com.justin.pocketmon.databinding.DialogPlanTodoBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.plan.PlanAdapter
import com.justin.pocketmon.plan.edit.PlanEditAdapter
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
        val document = db.collection("Comments").document()

        binding.addCommentButton.setOnClickListener{

            val comment = Comment()
            comment.id = document.id

            viewModel.addComment.value?.let {
                Log.d("justin","初檢查從detail帶過來的資料 => $comment ")
                comment.articleId = it.id
                comment.authorId = UserManager.user.id
                comment.authorName = UserManager.user.name
                comment.authorImage = UserManager.user.image
                comment.content = binding.commentEdit.text.toString()
                comment.createdTime = com.google.firebase.Timestamp.now()

            }
            viewModel.addComment(comment)
            Log.d("justin","再檢查從detail帶過來的資料 => $comment ")
            viewModel.navigateToDetailPage()


//            val articledata = viewModel.addComment.value!!
//
//            articledata.comment.add(binding.commentEdit.text.toString())
//            Logger.i("articledata.comment = ${articledata.comment}")
////             plan.method = binding.planTodoEdit.text
//
//            Logger.d("first check for data from PlanEditPage => $articledata")
//
//            viewModel.addComment(articledata)
//            Logger.d("再檢查從detailPage帶過來的資料 => $articledata")
//
//            viewModel.navigateToDetailPage()
////            viewModel.getComment()

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