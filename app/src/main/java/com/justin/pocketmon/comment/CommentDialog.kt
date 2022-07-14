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
        val document = db.collection("Article").document()

        binding.addCommentButton.setOnClickListener{

            val articledata = viewModel.addComment.value!!

            articledata.comment.add(binding.commentEdit.text.toString())
            Logger.i("articledata.comment = ${articledata.comment}")
//             plan.method = binding.planTodoEdit.text

            Logger.d("first check for data from PlanEditPage => $articledata")

            viewModel.addComment(articledata)
            Logger.d("再檢查從detailPage帶過來的資料 => $articledata")

            viewModel.navigateToDetailPage()
            viewModel.getComment()


//            binding.planEditPlanDegreeText.text = it.toString()
//
//            if (it >= 100L) {
//
//                val db = FirebaseFirestore.getInstance()
//                val document = db.collection("Broadcasts").document()
//
//                val broadcast = Broadcast()
//                broadcast.id = document.id
//                // livedata 必須要 .value 才能夠賦值
//                viewModel.planEdit.value?.let {
//                    broadcast.title = it.title
//                    broadcast.from = it.ownerId
//                    broadcast.timeFinish = com.google.firebase.Timestamp.now()
//                    broadcast.timeStart = it.createdTime.toString()
//                    Log.d("justin","檢查 -上傳前- broadcast 長這樣 => $broadcast ")
//                }
//
//                viewModel.publishToBroadcast(broadcast)
////                findNavController().navigate(NavigationDirections.navigateToIntroFragment())
////
//            }




        }

//       //recyclerView
//        val adapter = CommentAdapter()
//        binding.commentRecyclerview.adapter = adapter
//
//        // recyclerView
//        viewModel.commentAdded.observe(viewLifecycleOwner, Observer {
//
//            (binding.commentRecyclerview.adapter as CommentAdapter).submitList(it)
//            (binding.commentRecyclerview.adapter as CommentAdapter).notifyDataSetChanged()
//            binding.swipeRefreshLayout.isRefreshing = false

//            it.method.let {
//
//                adapter.submitList(it)
//            }

//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).submitList(it)
//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).notifyDataSetChanged()


//            binding.swipeRefreshLayout.isRefreshing = false
//            Logger.i("second viewModel.planEdit = $it")
//            viewModel.getToDoResult(Plan(
//                id = "fzKBm4kriaxT3qMnCGFW"
//            ))

//            Logger.i("Justin Livedata todo list = $it")

//        })
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.getComment()
//        }


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