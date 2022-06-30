package com.justin.pocketmon.plan.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.ItemPlanBinding
import com.justin.pocketmon.databinding.ItemPlanEditBinding
import com.justin.pocketmon.plan.PlanAdapter

class PlanEditAdapter () :
    ListAdapter<Plan, RecyclerView.ViewHolder>(DiffCallback) {

    class PlanEditViewHolder(private var binding: ItemPlanEditBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan) {

            binding.viewModel = plan
//            binding.itemPlanEditTodo.text = plan.method


            binding.executePendingBindings()
            binding.root

        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Plan>() {
        override fun areItemsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_PLAN = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_PLAN -> PlanEditViewHolder(
                ItemPlanEditBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is PlanEditViewHolder -> {
                holder.bind((getItem(position) as Plan))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_PLAN
    }
}









