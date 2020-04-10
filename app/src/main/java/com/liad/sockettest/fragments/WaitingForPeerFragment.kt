package com.liad.sockettest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liad.sockettest.R
import com.liad.sockettest.managers.SocketWebManager
import com.liad.sockettest.models.ChatUser
import com.liad.sockettest.utills.Constants
import com.liad.sockettest.utills.extensions.changeFragment
import com.liad.sockettest.utills.extensions.generateJSON
import com.liad.sockettest.utills.extensions.log
import kotlinx.android.synthetic.main.fragment_waiting_for_peer.*
import org.json.JSONObject
import java.util.*

class WaitingForPeerFragment : Fragment() {

    companion object {
        fun newInstance(args: Bundle? = null): WaitingForPeerFragment {
            val waitingForPeerFragment =
                WaitingForPeerFragment()
            waitingForPeerFragment.arguments = args
            return waitingForPeerFragment
        }
    }

    private val mSocket = SocketWebManager.getInstance()
    private lateinit var chatUser: ChatUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_waiting_for_peer, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startPeering()
        setSocketListeners()
        waiting_for_peer_fragment_progress_bar.setOnClickListener {
            moveToChatFragment()
        }
    }

    private fun startPeering() {
        val uuid = UUID.randomUUID()
        mSocket.emit(
            SocketWebManager.READY_FOR_PEERING,
            generateJSON(Constants.USER_ID to uuid, Constants.NAME to "Rotem")
        )
    }

    private fun setSocketListeners() {
        mSocket.on(SocketWebManager.PEER_FOUND) {
            val obj = it[0] as JSONObject
            log("PEER_FOUND $obj")

            val userId = obj.getString(Constants.USER_ID)
            val name = obj.getString(Constants.NAME)
            val socketId = obj.getString(Constants.SOCKET_ID)
            chatUser = ChatUser(socketId, userId, name)
            log("$chatUser")
            moveToChatFragment()
        }
    }

    private fun moveToChatFragment() {
        activity?.also { activity ->
            val bundle = Bundle()
            bundle.putString(Constants.USER_ID, chatUser.userId)
            changeFragment(activity, ChatFragment.newInstance(), bundle, true)
        }
    }
}
