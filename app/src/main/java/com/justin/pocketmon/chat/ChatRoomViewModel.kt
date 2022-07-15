package com.justin.pocketmon.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Chat
import com.justin.pocketmon.data.Chatroom
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.User
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.ChatItem
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ChatRoomViewModel(
    val argument: Chatroom,
    private val repository: PocketmonRepository
) : ViewModel() {

    private val _plan = MutableLiveData<Chatroom>().apply {
        value = argument
    }
    val plan: LiveData<Chatroom>
        get() = _plan

    private val _checkChatroom = MutableLiveData<Chatroom>()
    val checkChatroom: LiveData<Chatroom>
        get() = _checkChatroom

    private val _addChatroom = MutableLiveData<Chatroom>().apply {
        _plan.value?.let { plan ->
            UserManager.userId?.let { userId ->
                value =
                    Chatroom(plan.id, plan.name, null, null
                    )
            }
        }
    }
    val addChatroom: LiveData<Chatroom>
        get() = _addChatroom

    private val _chatItem = MutableLiveData<List<ChatItem>>()
    val chatItem: LiveData<List<ChatItem>>
        get() = _chatItem

    private val _chat = MutableLiveData<Chat>()
    val chat: LiveData<Chat>
        get() = _chat

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    var liveChatItem = MutableLiveData<List<Chat>>()

    var observeChatItem = MutableLiveData<Boolean>()


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.d("------------------------------------------")
        Logger.d("$this")
        Logger.d("------------------------------------------")

//        getGroupChatroomResult()
//        getUserResult()

    }



//    fun getGroupChatroomResult() {
//        coroutineScope.launch {
//            _status.value = LoadApiStatus.LOADING
//            Logger.d("getGroupChatroomResult is start")
//
//            val getChatroomResult = _plan.value?.let { repository.getGroupChatroom(it.id) }
//            Logger.d("getGroupChatroomResult=$getChatroomResult")
//            _checkChatroom.value = when (getChatroomResult) {
//
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    getChatroomResult.data
//                }
//                is Result.Fail -> {
//                    _error.value = getChatroomResult.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = getChatroomResult.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//        }
//    }

//    fun addChatroomResult() {
//        coroutineScope.launch {
//            Logger.d("_addChatroom.value=${_addChatroom.value}")
//            when (val addChatroomResult = repository.addChatroom(_addChatroom.value!!)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//
//                }
//                is Result.Fail -> {
//                    _error.value = addChatroomResult.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = addChatroomResult.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }

//    fun getChatsResult() {
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = _checkChatroom.value?.let { repository.getChats(it.id) }
//            Logger.d("getChatsResult result=$result")
//
//            _chatItem.value = when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    chatToChatItem(result.data)
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//        }
//    }

    fun chatToChatItem(chats: List<Chat>): List<ChatItem> {
        val newItems = mutableListOf<ChatItem>()
        Logger.d("chatToChatItem chats=$chats")
        for (chat in chats) {
            if (chat.senderId == UserManager.userId) {
                newItems.add(ChatItem.UserSide(chat))
            } else {
                newItems.add(ChatItem.OtherSide(chat))
            }
        }
        return newItems
    }

//    fun getUserResult() {
//        coroutineScope.launch {
//            _status.value = LoadApiStatus.LOADING
//            Timber.d("getUserResult is start")
//
//            val result = UserManager.userId?.let { repository.getUser(it) }
//            _user.value = when (result) {
//
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//        }
//    }

//    fun sendMessageResult(content: String) {
//        Logger.d("_user.value = ${_user.value}")
//        Logger.d("chat.value = ${chat.value}")
//        _user.value?.let {
//            _chat.value = Chat("", it.id, Date(), content, it.image, it.nickname)
//        }
//        Logger.d("_chat.value = ${_chat.value}")
//
//        coroutineScope.launch {
//            _status.value = LoadApiStatus.LOADING
//            when (val result = _checkChatroom.value?.let { chatroom ->
//                _chat.value?.let { chat ->
//                    repository.sendChat(
//                        chatroom.id,
//                        chat
//                    )
//                }
//            }) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }

//    fun addChatroomMessageAndTimeResult() {
//        coroutineScope.launch {
//            _status.value = LoadApiStatus.LOADING
//            when (val result =
//                _checkChatroom.value?.let { chatroom ->
//                    _chat.value?.let { chat ->
//                        repository.addChatroomMessageAndTime(chatroom.id, chat.content)
//                    }
//                }) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }

    fun getLiveChatsResult() {
        liveChatItem = repository.getLiveChats(_checkChatroom.value!!.id)
        observeChatItem.value = true
        _status.value = LoadApiStatus.DONE

    }

}