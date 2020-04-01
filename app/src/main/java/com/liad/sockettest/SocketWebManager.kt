package com.liad.sockettest

import com.github.nkzawa.socketio.client.IO

class SocketWebManager {

    companion object{
        const val READY_FOR_PEERING = "READY_FOR_PEERING"
        const val PEER_FOUND = "PEER_FOUND"
        const val CHAT_MESSAGE = "CHAT_MESSAGE"
        const val PEER_DISCONNECTED = "PEER_DISCONNECTED"
        const val CHAT_SOCKET_URL = "https://questions-chat-server.herokuapp.com/"

        fun getInstance() = IO.socket(CHAT_SOCKET_URL)
    }


}