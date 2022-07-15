package com.justin.pocketmon.chat

import androidx.fragment.app.Fragment
import com.justin.pocketmon.databinding.FragmentChatRoomBinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.util.Logger

//
class ChatroomFragment : Fragment() {

    private val viewModel by viewModels<ChatRoomViewModel> {
        getVmFactory(
            ChatroomFragmentArgs.fromBundle(
                requireArguments()
            ).chatroomKey
        )
    }

    lateinit var binding: FragmentChatRoomBinding

    companion object {
        fun newInstance() = ChatroomFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatRoomBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = ChatroomAdapter()

        binding.recyclerViewChat.adapter = adapter

        viewModel.chatItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.imageChatSend.setOnClickListener {
            if (binding.editTextChatInputMessage.text.toString() != "") {
                val content = binding.editTextChatInputMessage.text.toString()
//                viewModel.sendMessageResult(content)
//                viewModel.addChatroomMessageAndTimeResult()
                Logger.d("binding.imageGroupChatSend.setOnClickListener")
            }
            binding.editTextChatInputMessage.text.clear()
        }

        binding.imageChatBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.checkChatroom.observe(viewLifecycleOwner) {
            if (it == null) {
//                viewModel.addChatroomResult()
//                viewModel.getGroupChatroomResult()
            }else{
                if (PocketmonApplication.instance.isLiveDataDesign()) {
                    viewModel.getLiveChatsResult()
                } else {
//                    viewModel.getChatsResult()
                }
            }
        }

        viewModel.observeChatItem.observe(viewLifecycleOwner) {
            Logger.i("viewModel.liveChats.observe, it=$it")
            if (it) {

                viewModel.liveChatItem.observe(viewLifecycleOwner) { ListChat ->
                    val chats = viewModel.chatToChatItem(ListChat)
                    adapter.submitList(chats)
                    binding.recyclerViewChat.smoothScrollToPosition(chats.size)
                }


            }
        }

        return binding.root
    }

}