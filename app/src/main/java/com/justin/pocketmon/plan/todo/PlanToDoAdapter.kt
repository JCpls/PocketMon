package com.justin.pocketmon.plan.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.ItemDreamHomeBinding
import com.justin.pocketmon.databinding.ItemPlanEditBinding


class PlanToDoAdapter(private val onClickListener: OnClickListener): ListAdapter<Plan, RecyclerView.ViewHolder>(DiffCallback){



    class PlanEditViewHolder(private var binding: ItemPlanEditBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan, onClickListener: OnClickListener) {

            binding.planData = plan
            binding.itemPlanEditTodo.text = plan.method.toString()


            binding.root.setOnClickListener{onClickListener.onClick(plan)}

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PlanEditViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPlanEditBinding.inflate(layoutInflater, parent, false)

                return PlanEditViewHolder(binding)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanEditViewHolder {
        return PlanEditViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlanEditViewHolder -> {
                val plan = getItem(position) as Plan

                holder.bind(plan, onClickListener)
            }
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<Plan>() {
        override fun areItemsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Plan, newItem: Plan): Boolean {
            return oldItem == newItem
        }

    }
    class OnClickListener(val clickListener: (plan: Plan) -> Unit) {
        fun onClick(plan: Plan) = clickListener(plan)
    }

}