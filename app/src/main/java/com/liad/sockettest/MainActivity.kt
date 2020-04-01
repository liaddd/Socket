package com.liad.sockettest

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mSocket = SocketWebManager.getInstance()
    private lateinit var secondUserId: String
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSocket()
        setButtonsListeners()
    }

    private fun setButtonsListeners() {
        send_message_button.setOnClickListener {
            sendMessage()
        }

        start_button.setOnClickListener {
            startPeering()
        }
    }

    private fun startPeering() {
        val uuid = UUID.randomUUID()
        mSocket.emit(
            SocketWebManager.READY_FOR_PEERING,
            JSONObject().put("userId", uuid).put("name", "Liad")
        )
    }

    private fun sendMessage() {
        val message: EditText = socket_edit_text
        mSocket.emit(
            SocketWebManager.CHAT_MESSAGE,
            JSONObject().put("to", secondUserId).put("message", message.text.toString())
        )
        message.text.clear()
    }

    private fun initSocket() {
        mSocket.on(Socket.EVENT_CONNECT) {
            Log.d("Liad", "Connected!")
        }

        mSocket.on(SocketWebManager.PEER_FOUND) {
            val obj = it[0] as JSONObject
            secondUserId = obj.getString("userId")
            Log.d("Liad", "PEER_FOUND $obj")
        }

        mSocket.on(SocketWebManager.CHAT_MESSAGE) {
            val obj = it[0] as JSONObject
            Log.d("Liad", "SocketWebManager.CHAT_MESSAGE $obj")
        }

        mSocket.on(SocketWebManager.PEER_DISCONNECTED) {
            Log.d("Liad", "Disconnected")
        }

        mSocket.connect()
    }


    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
        mSocket.off(SocketWebManager.PEER_FOUND)

    }
}
