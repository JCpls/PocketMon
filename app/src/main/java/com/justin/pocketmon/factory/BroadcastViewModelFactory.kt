package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.chat.ChatRoomViewModel
import com.justin.pocketmon.chat.ChatViewModel
import com.justin.pocketmon.comment.CommentViewModel
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Broadcast
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class BroadcastViewModelFactory constructor(
    private val broadcast: Broadcast,
    private val repository: PocketmonRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(ChatRoomViewModel::class.java) ->
                    ChatRoomViewModel(broadcast, repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}