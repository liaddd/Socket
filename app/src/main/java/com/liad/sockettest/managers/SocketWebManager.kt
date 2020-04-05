package com.liad.sockettest.managers

import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

class SocketWebManager {

    companion object {

        private lateinit var mSocket: Socket

        const val READY_FOR_PEERING = "READY_FOR_PEERING"
        const val PEER_FOUND = "PEER_FOUND"
        const val CHAT_MESSAGE = "CHAT_MESSAGE"
        const val MESSAGE = "message"
        const val PEER_DISCONNECTED = "PEER_DISCONNECTED"
        private const val CHAT_SOCKET_URL = "https://questions-chat-server.herokuapp.com/"

        fun getInstance(): Socket {
            mSocket = IO.socket(CHAT_SOCKET_URL)
            return mSocket
        }

    }

}