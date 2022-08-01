package com.justin.pocketmon.plan

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.databinding.ItemPlanBinding
import java.text.SimpleDateFormat
import kotlin.time.Duration.Companion.seconds

class PlanAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<Plan, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (plan: Plan) -> Unit) {
        fun onClick(plan: Plan) = clickListener(plan)
    }

    class ArticleViewHolder(private var binding: ItemPlanBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan, onClickListener: OnClickListener) {

            binding.itemPlan = plan
            binding.planTitle.text = plan.title
            binding.planDegreeText.text = plan.degree.toString()

            if (plan.degree.toString() == "0") {
                binding.planDegreeText.setTextColor(Color.rgb(0, 0, 200))
            } else if (plan.degree.toString() == "1") {
                binding.planDegreeText.setTextColor(Color.rgb(0, 0, 200))
            } else if (plan.degree.toString() == "2") {
                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
            } else if (plan.degree.toString() == "3") {
                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
            } else if (plan.degree.toString() == "4") {
                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
            } else {
                binding.planDegreeText.setTextColor(Color.rgb(160, 0, 160))
            }

            binding.root.setOnClickListener { onClickListener.onClick(plan) }
            binding.executePendingBindings()
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
            ITEM_VIEW_TYPE_PLAN -> ArticleViewHolder(ItemPlanBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ArticleViewHolder -> {
                holder.bind((getItem(position) as Plan), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_PLAN
    }
}

