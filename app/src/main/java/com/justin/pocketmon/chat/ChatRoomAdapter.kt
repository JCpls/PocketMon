package com.justin.pocketmon.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Chat
import com.justin.pocketmon.databinding.FragmentChatOtherBinding
import com.justin.pocketmon.databinding.FragmentChatRoomBinding
import com.justin.pocketmon.databinding.FragmentChatUserBinding
import com.justin.pocketmon.util.ChatItem
import com.justin.pocketmon.util.TimeFormater

class ChatroomAdapter : ListAdapter<ChatItem, RecyclerView.ViewHolder>(DiffCallback) {

    class UserSideViewHolder(private var binding: FragmentChatUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chat = chat
            binding.textChatOwnerTime.text = TimeFormater.getTime(chat.createdTime.time)
            binding.executePendingBindings()
        }
    }

    class OtherSideViewHolder(private var binding: FragmentChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.chat = chat
            binding.textChatOtherTime.text = TimeFormater.getTime(chat.createdTime.time)
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.createdTime == newItem.createdTime
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.createdTime == newItem.createdTime
        }

        private const val ITEM_VIEW_TYPE_USERSIDE = 0x00
        private const val ITEM_VIEW_TYPE_OTHERSIDE = 0x01

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_USERSIDE -> UserSideViewHolder(
                FragmentChatUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_OTHERSIDE -> OtherSideViewHolder(
                FragmentChatOtherBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserSideViewHolder -> {
                holder.bind((getItem(position) as ChatItem.UserSide).chat)
            }
            is OtherSideViewHolder -> {
                holder.bind((getItem(position) as ChatItem.OtherSide).chat)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatItem.UserSide -> ITEM_VIEW_TYPE_USERSIDE
            is ChatItem.OtherSide -> ITEM_VIEW_TYPE_OTHERSIDE
        }

    }
}