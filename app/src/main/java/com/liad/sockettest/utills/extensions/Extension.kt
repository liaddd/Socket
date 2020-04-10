package com.liad.sockettest.utills.extensions

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.liad.sockettest.R
import com.liad.sockettest.models.ChatMessage
import com.liad.sockettest.models.ChatUser
import com.liad.sockettest.utills.Constants
import org.json.JSONObject

fun changeFragment(
    activity: FragmentActivity,
    fragment: Fragment,
    bundle: Bundle? = null,
    addToStack: Boolean = false
) {
    val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
    if (addToStack) fragmentTransaction.addToBackStack(null)
    fragment.arguments = bundle
    fragmentTransaction.replace(
        R.id.main_activity_frame_layout,
        fragment/*,
        fragment::class.simpleName*/
    ).commit()
}

fun log(output: String, key: String = "Liad") {
    Log.d(key, output)
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (text.isNotEmpty()) Toast.makeText(this, text, duration).show()
}

fun generateJSON(vararg data: Pair<String, Any>): JSONObject {
    val jsonObject = JSONObject()
    for (pair in data) {
        val key = pair.first
        val value = pair.second
        jsonObject.put(key, value)
    }
    return jsonObject
}

fun createChatMessageFromJson(obj: JSONObject): ChatMessage? {
    var chatMessage: ChatMessage? = null
    if (obj.length() > 0) {
        val message = obj.getString(Constants.MESSAGE)
        val from = obj.getJSONObject(Constants.FROM)
        var chatUser: ChatUser? = null
        if (from.length() > 0) {
            val userId = from.getString(Constants.USER_ID)
            val name = from.getString(Constants.NAME)
            val socketId = from.getString(Constants.SOCKET_ID)
            chatUser = ChatUser(socketId, userId, name)
        }
        val to = obj.getString(Constants.TO)
        val timestamp = obj.getLong(Constants.TIMESTAMP)
        val type = obj.getString(Constants.TYPE)
        chatMessage = ChatMessage(to, message, chatUser, timestamp, type)
    }
    return chatMessage
}

