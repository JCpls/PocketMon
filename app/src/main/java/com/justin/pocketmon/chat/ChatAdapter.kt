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

            binding.itemBroadcast = broadcast
            binding.broadcastTitle.text = broadcast.title
            binding.broadcastFromWho.text = broadcast.fromName
            binding.broadcastTimeFinish.text = SimpleDateFormat("MM/dd, yyyy", Locale.CHINESE).format(Timestamp.now().toDate())

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