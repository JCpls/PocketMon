package com.justin.pocketmon.plan.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.PocketmonApplication
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

//        binding.planEditRecyclerView.adapter = PlanEditAdapter(
//
//                viewModel.addCheckboxStatus(it)
////                viewModel.navigateToDetail(it)
//        )

        // recyclerView
        viewModel.planEdit.observe(viewLifecycleOwner, Observer {

                it.method.let {
                    viewModel.getDegree()
//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).submitList(it)
//              (binding.planEditRecyclerView.adapter as PlanEditAdapter).notifyDataSetChanged()
                adapter.submitList(it)

                }

//            binding.swipeRefreshLayout.isRefreshing = false
            Logger.i("second viewModel.planEdit = $it")
//            viewModel.getToDoResult(Plan(
//                id = "fzKBm4kriaxT3qMnCGFW"
//            ))

            Logger.i("Justin Livedata todo list = $it")

        })

        viewModel.newDegree.observe(viewLifecycleOwner, Observer {
            Logger.i("newDegree is -> $it")

            binding.planEditPlanDegreeText.text = it.toString()
            Logger.i("observe有無啟動")
        })






//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.getArticlesResult()
//        }

        // check checkbox to send change to firebase
//        viewModel.planEdit.observe(viewLifecycleOwner, Observer {
//
//            it.degree = it.degree + it.method[1]
//
//                viewModel.addCheckboxStatus()
//            }
//        })


//        binding..setOnClickListener {
//            if (!checkedItems.get(adapterPosition, false)) {//checkbox checked
//                binding.checkBox.isChecked = true
//                checkedItems.put(adapterPosition, true)
//                Logger.d("checkedItems $checkedItems")
//                Logger.d("binding.checkBox.isChecked.toString() ${binding.checkBox.text}")
//                val a = FishTodayCategory(
//                    "",
//                    binding.editTextUnit.text.toString(),
//                    binding.checkBox.text.toString(),
//                    binding.editTextPrice.text.toString(),
//                    binding.spinner2.selectedItem.toString(),
//                    "",
//                    "",
//                    ""
//                )
//            }
//        }


//        checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
//            checkBoxStateArray.put(bindingAdapterPosition, isChecked)
//        })


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

        viewModel.scoreSelected.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {

                    //呼叫viewModel裡面的計算式
//                    viewModel.addScoreToDegree()

                }
            }
        )

        binding.planEditPlanDegreeText


        return binding.root
    }
}
