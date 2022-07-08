package com.justin.pocketmon.plan.edit

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.PlanMethod
import com.justin.pocketmon.databinding.ItemPlanEditBinding
import com.justin.pocketmon.util.Logger

class PlanEditAdapter (var viewModel: PlanEditViewModel) :
    ListAdapter<PlanMethod, RecyclerView.ViewHolder>(DiffCallback) {



    class OnItemSelectedListener(val isSelectedListener: (planMethod: PlanMethod) -> Unit){
        fun isSelected(planMethod: PlanMethod) = isSelectedListener(planMethod)
    }

    var checkedItems = SparseBooleanArray()

//    class OnSelectionChangedListener(val selectListener: (Plan) -> Unit) {
//        fun isSelected(planMethod: PlanMethod) = selectListener(plan)
//    }

    class PlanEditViewHolder(var binding: ItemPlanEditBinding, var viewModel: PlanEditViewModel):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: PlanMethod) {

            binding.planData = plan.toString()
            binding.itemPlanEditTodo.text = plan.todo
            binding.itemPlanEditScore.text = plan.score
            binding.itemTodoCheckbox.isChecked = plan.done



            binding.executePendingBindings()


            binding.itemTodoCheckbox.setOnClickListener {
                Logger.i("binding.itemTodoCheckbox.isChecked = ${binding.itemTodoCheckbox.isChecked}")
                binding.itemTodoCheckbox.isChecked.let{

                    if(it==true){
                        viewModel.add(plan.score)
                    } else {
                        viewModel.minus(plan.score)
                    }
                }


//                binding.itemTodoCheckbox.isChecked = true

//                if (!checkedItems.get(adapterPosition, false)) {//checkbox checked
//                    binding.itemTodoCheckbox.isChecked = true
//                    checkedItems.put(adapterPosition, true)
//                    Logger.d("checkedItems $checkedItems")
//                    Logger.d("binding.checkBox.isChecked.toString() ${binding.itemTodoCheckbox.text}")
//                }
            }
//            binding.itemTodoCheckbox.setOnClickListener(
//
//            )
        }
    }


//        init {
//
//            binding.itemTodoCheckbox.setOnClickListener {
//                if (!checkedItems.get(adapterPosition, false)) {//checkbox checked
//                    binding.itemTodoCheckbox.isChecked = true
//                    checkedItems.put(adapterPosition, true)
//                    Logger.d("checkedItems $checkedItems")
//                    Logger.d("binding.checkBox.isChecked.toString() ${binding.itemTodoCheckbox.text}")
//
//                    val a = Plan(
//
//                        "",
//                        "",
//                        "",
//                        "List(St",
//                        0L, ,
//                        "",
//                        ""
//                        "true, "
//                    )
//
//                    }
//
//                    viewModel.addCheckboxStatus(it)
//
//                    Logger.i("binding.checkBox.setOnClickListener a=> ${a}")
//
//                }
//            }


        companion object DiffCallback : DiffUtil.ItemCallback<PlanMethod>() {
            override fun areItemsTheSame(oldItem: PlanMethod, newItem: PlanMethod): Boolean {
                return oldItem === newItem
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

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            when (holder) {
                is PlanEditViewHolder -> {
                    holder.binding.itemTodoCheckbox.isChecked = checkedItems.get(position, false)
                    holder.bind((getItem(position) as PlanMethod))
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return ITEM_VIEW_TYPE_PLAN
        }

    }


//public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            MyViewHolder myViewHolder = (MyViewHolder) holder;
//            myViewHolder.checkBox.setText(content.get(position));
//
//            myViewHolder.checkBox.setOnCheckedChangeListener(null);//先設定一次CheckBox的選中監聽器，傳入引數null
//            myViewHolder.checkBox.setChecked(flag[position]);//用陣列中的值設定CheckBox的選中狀態
//
//            //再設定一次CheckBox的選中監聽器，當CheckBox的選中狀態發生改變時，把改變後的狀態儲存在陣列中
//            myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    flag[position] = b;
//                }
//            });
//        }








