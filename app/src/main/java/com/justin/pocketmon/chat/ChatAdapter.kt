package com.justin.pocketmon.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Broadcast
import com.justin.pocketmon.databinding.ItemBroadcastBinding
import com.justin.pocketmon.util.Logger
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class ChatAdapter(private val onClickListener: OnClickListener ) :
    ListAdapter<Broadcast, RecyclerView.ViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (broadcast:Broadcast) -> Unit) {
        fun onClick(broadcast:Broadcast) = clickListener(broadcast)
    }

    class BroadcastViewHolder(private var binding: ItemBroadcastBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(broadcast: Broadcast, onClickListener: OnClickListener) {

//            val time = System.currentTimeMillis().toString()
//            val timeNew = SimpleDateFormat("E, d MMM yyyy HH:mm").format(time)

//            val time = java.sql.Timestamp(System.currentTimeMillis())
//            val timeNew = SimpleDateFormat("E, d MMM yyyy HH:mm").format(broadcast.timeStart)
//            Logger.i("timeNew = $timeNew")

            binding.itemBroadcast = broadcast
            binding.broadcastTitle.text = broadcast.title
            binding.broadcastFromWho.text = broadcast.fromName
            binding.broadcastTimeFinish.text = SimpleDateFormat("MM/dd, yyyy", Locale.CHINESE).format(broadcast.timeFinish.toDate())


//            fun dateToday(): String {
//                val today = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH).format(Timestamp.now().toDate())
//                Logger.i("today = $today")
//                return today
//            }


//            if (plan.degree.toString() == "0") {
//                binding.planDegreeText.setTextColor(Color.rgb(0, 0, 200))
//            } else if (plan.degree.toString() == "1") {
//                binding.planDegreeText.setTextColor(Color.rgb(0, 0, 200))
//            } else if (plan.degree.toString() == "2") {
//                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
//            } else if (plan.degree.toString() == "3") {
//                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
//            } else if (plan.degree.toString() == "4") {
//                binding.planDegreeText.setTextColor(Color.rgb(0, 160, 0))
//            } else {
//                binding.planDegreeText.setTextColor(Color.rgb(160, 0, 160))
//            }

//            binding.textAuthorName.text = plan.ownerId
//            binding.textContent.text = plan.description[0]
//            //
//            binding.textContent2.text = plan.description[1]

//            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
//            val time = broadcast.timeStart?.seconds?.times(1000L)
//            val dataTime = sdf.format(time)
//            binding.textTime.text = dataTime


            binding.root.setOnClickListener { onClickListener.onClick(broadcast) }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Broadcast>() {
        override fun areItemsTheSame(oldItem: Broadcast, newItem: Broadcast): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Broadcast, newItem: Broadcast): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_BROADCAST = 0x00
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_BROADCAST -> BroadcastViewHolder(
                ItemBroadcastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is BroadcastViewHolder -> {
                holder.bind((getItem(position) as Broadcast), onClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_VIEW_TYPE_BROADCAST
    }
}

