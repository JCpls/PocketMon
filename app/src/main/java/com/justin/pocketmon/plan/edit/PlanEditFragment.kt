package com.justin.pocketmon.plan.edit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.data.Broadcast
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.FragmentPlanEditBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.util.Logger

class PlanEditFragment: Fragment() {
    private val viewModel by viewModels<PlanEditViewModel> { getVmFactory(PlanEditFragmentArgs.fromBundle(requireArguments()).planKey) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPlanEditBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.isLiveDataDesign = PocketmonApplication.instance.isLiveDataDesign()
        binding.viewModel = viewModel


        val adapter = PlanEditAdapter(viewModel)
        binding.planEditRecyclerView.adapter = adapter


        // recyclerView
        viewModel.planEdit.observe(viewLifecycleOwner, Observer {

                it.method.let {


                    viewModel.getDegree()

                    adapter.submitList(it)

                    // - dead circle of observe and upload -- paralyze firebase
//                    viewModel.getToDoResult(plan)

//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).submitList(it)
//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).notifyDataSetChanged()
                }

//            binding.swipeRefreshLayout.isRefreshing = false
            Logger.i("second viewModel.planEdit = $it")
//                id = "fzKBm4kriaxT3qMnCGFW"
//            ))
            Logger.i("Justin Livedata todo list = $it")

        })

        // observe for checkBox status in recyclerView
        viewModel.newDegree.observe(viewLifecycleOwner, Observer {
            Logger.i("newDegree is -> $it")

            binding.planEditPlanDegreeText.text = it.toString()

            if (it >= 100L) {

                val db = FirebaseFirestore.getInstance()
                val document = db.collection("Broadcasts").document()

                val broadcast = Broadcast()
                broadcast.id = document.id
                // livedata 必須要 .value 才能夠賦值
                viewModel.planEdit.value?.let {
                    broadcast.title = it.title
                    broadcast.from = it.ownerId
                    broadcast.timeFinish = com.google.firebase.Timestamp.now()
                    broadcast.timeStart = it.createdTime.toString()
                    Log.d("justin","檢查 -上傳前- broadcast 長這樣 => $broadcast ")
                }

                viewModel.publishToBroadcast(broadcast)
//                findNavController().navigate(NavigationDirections.navigateToIntroFragment())
//
            }

            Logger.i("observe有無啟動")
        })


//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.getArticlesResult()
//        }


        viewModel.leavePlanEdit.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                   findNavController().navigate(NavigationDirections.navigateToPlanFragment())
                }
            }
        )

        viewModel.navigateToPlanTodo.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToPlanTodoDialog(it))
                    viewModel.onPlanEditToPlanTodoNavigated()
                }
            }
        )


        return binding.root
    }
}
