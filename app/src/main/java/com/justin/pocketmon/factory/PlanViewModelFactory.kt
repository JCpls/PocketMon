package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.chat.ChatRoomViewModel
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.User
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.detail.DetailViewModel
import com.justin.pocketmon.plan.PlanViewModel
import com.justin.pocketmon.plan.edit.PlanEditViewModel
import com.justin.pocketmon.plan.todo.PlanToDoViewModel

@Suppress("UNCHECKED_CAST")
class PlanViewModelFactory constructor(
    private val plan: Plan,
    private val repository: PocketmonRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(PlanEditViewModel::class.java) ->
                    PlanEditViewModel(plan, repository)

                isAssignableFrom(PlanToDoViewModel::class.java) ->
                    PlanToDoViewModel(plan, repository)

//                isAssignableFrom(ChatRoomViewModel::class.java) ->
//                    ChatRoomViewModel(plan, repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}