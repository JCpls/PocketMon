package com.justin.pocketmon.plan.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.NavigationDirections
import com.justin.pocketmon.data.PlanMethod
import com.justin.pocketmon.databinding.DialogPlanTodoBinding
import com.justin.pocketmon.ext.getVmFactory


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
        binding.planTodoButton.setOnClickListener{

            val plan = viewModel.addedTodo.value!!

            plan.method.add(PlanMethod(done = false, score = binding.planTodoScore.text.toString(), todo = binding.planTodoEdit.text.toString()))

            viewModel.addToDo(plan)

            dismiss()

        }

        viewModel.navigateToPlanEditPage.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToPlanEditFragment(it))
                    viewModel.onToDotoPlanEditNavigated()
                }
            }
        )

        //seekbar for degree of completion
        binding.seekbarPlanTodo.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, process: Int, fromUser: Boolean) {
                binding.planTodoScore.text = process.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        return binding.root
    }
}
