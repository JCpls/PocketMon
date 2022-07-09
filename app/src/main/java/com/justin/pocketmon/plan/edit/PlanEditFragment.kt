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
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.PocketmonApplication
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

//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).submitList(it)
//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).notifyDataSetChanged()
                }

//            binding.swipeRefreshLayout.isRefreshing = false
            Logger.i("second viewModel.planEdit = $it")
//            viewModel.getToDoResult(Plan(
//                id = "fzKBm4kriaxT3qMnCGFW"
//            ))

            Logger.i("Justin Livedata todo list = $it")

        })

        // observe for checkBox status in recyclerView
        viewModel.newDegree.observe(viewLifecycleOwner, Observer {
            Logger.i("newDegree is -> $it")

            binding.planEditPlanDegreeText.text = it.toString()

            if (it >= 100L) {
                Logger.i("it>= 100L")
//                viewModel.
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


//        binding.planEditPlanDegreeText


        return binding.root
    }
}
