package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.chat.ChatRoomViewModel
import com.justin.pocketmon.data.Chatroom
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.source.PocketmonRepository

@Suppress("UNCHECKED_CAST")
class ChatroomViewModelFactory constructor(
    private val chatroom: Chatroom,
    private val repository: PocketmonRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(ChatRoomViewModel::class.java) ->
                    ChatRoomViewModel(chatroom, repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}