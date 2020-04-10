package com.liad.sockettest.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.liad.sockettest.R
import com.liad.sockettest.adapters.ChatAdapter
import com.liad.sockettest.managers.SocketWebManager
import com.liad.sockettest.models.ChatMessage
import com.liad.sockettest.utills.Constants
import com.liad.sockettest.utills.extensions.createChatMessageFromJson
import com.liad.sockettest.utills.extensions.generateJSON
import com.liad.sockettest.utills.extensions.log
import kotlinx.android.synthetic.main.fragment_chat.*
import org.json.JSONObject

class ChatFragment : Fragment() {

    companion object {
        fun newInstance(args: Bundle? = null): ChatFragment {
            val chatFragment = ChatFragment()
            chatFragment.arguments = args
            return chatFragment
        }
    }

    private val mSocket = SocketWebManager.getInstance()
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatRV: RecyclerView

    private var secondUserId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        secondUserId = arguments?.getString(Constants.USER_ID)
        initViews()
    }

    private fun initViews() {
        chatAdapter = ChatAdapter(secondUserId ?: "")
        chatRV = chat_fragment_recycler_view
        chatRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }
        setSocketListeners()
        chat_fragment_send_message_btn.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val message: TextInputEditText = chat_fragment_text_input_edit_text
        message.text?.isNotEmpty().run {
            if (secondUserId.isNullOrEmpty()) return@run
            mSocket.emit(
                SocketWebManager.CHAT_MESSAGE,
                generateJSON(
                    Constants.TO to secondUserId!!,
                    Constants.MESSAGE to message.text.toString()
                )
            )
            message.text?.clear()
        }
    }

    private fun setSocketListeners() {
        mSocket.on(SocketWebManager.CHAT_MESSAGE) {
            val obj = it[0] as JSONObject
            log("SocketWebManager.CHAT_MESSAGE $obj")
            val chatMessage = createChatMessageFromJson(obj)
            chatMessage?.let { chatMsgObj ->
                updateAdapter(chatMsgObj)
            }
        }

        mSocket.on(SocketWebManager.PEER_DISCONNECTED) {
            log("Disconnected")
        }
    }

    private fun updateAdapter(message: ChatMessage) {
        activity?.runOnUiThread {
            chatAdapter.addMessage(message)
            val layoutManager: RecyclerView.LayoutManager =
                chatRV.layoutManager as LinearLayoutManager
            chatRV.smoothScrollToPosition(layoutManager.itemCount)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }

}
