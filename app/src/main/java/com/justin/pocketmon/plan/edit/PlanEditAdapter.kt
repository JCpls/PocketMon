package com.justin.pocketmon.plan.edit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.PlanMethod
import com.justin.pocketmon.databinding.ItemPlanEditBinding
import com.justin.pocketmon.util.Logger

class PlanEditAdapter (var viewModel: PlanEditViewModel) :
    ListAdapter<PlanMethod, RecyclerView.ViewHolder>(DiffCallback) {

    class PlanEditViewHolder(var binding: ItemPlanEditBinding, var viewModel: PlanEditViewModel):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(planMethod: PlanMethod) {

            binding.planData = planMethod.toString()
            binding.itemPlanEditTodo.text = planMethod.todo
            binding.itemPlanEditScore.text = planMethod.score
            binding.itemTodoCheckbox.isChecked = planMethod.done

            Logger.i("binding.planData = ${planMethod}")
            Logger.i("adapter planMethod.todo = ${planMethod.todo}")
            Logger.i("adapter planMethod.score = ${planMethod.score}")
            Logger.i("adapter planMethod.done = ${planMethod.done}")

            binding.executePendingBindings()

            // binding the check box to call functions(add,minus) in viewModel
            binding.itemTodoCheckbox.setOnClickListener {

                Logger.i("binding.itemTodoCheckbox.isChecked = ${binding.itemTodoCheckbox.isChecked}")

                binding.itemTodoCheckbox.isChecked.let{
                    if(it==true){

                        binding.itemTodoCheckbox.isChecked

                        viewModel.addPoint(planMethod.score)
                        viewModel.DoneIsTrue(value = true, planMethod.todo )

                    } else {

                        viewModel.minusPoint(planMethod.score)
                        viewModel.DoneIsFalse(value = false, planMethod.todo)

                    }
                }
            }
        }
    }

        companion object DiffCallback : DiffUtil.ItemCallback<PlanMethod>() {
            override fun areItemsTheSame(oldItem: PlanMethod, newItem: PlanMethod): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PlanMethod, newItem: PlanMethod): Boolean {
                return oldItem == newItem
            }

            private const val ITEM_VIEW_TYPE_PLAN = 0x00
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                ITEM_VIEW_TYPE_PLAN -> PlanEditViewHolder(
                    ItemPlanEditBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), viewModel
                )

                else -> throw ClassCastException("Unknown viewType $viewType")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            when (holder) {
                is PlanEditViewHolder -> {
                    holder.bind((getItem(position) as PlanMethod))
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return ITEM_VIEW_TYPE_PLAN
        }
}








