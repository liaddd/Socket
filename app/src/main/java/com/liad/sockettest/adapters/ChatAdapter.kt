package com.liad.sockettest.adapters

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.liad.sockettest.R
import com.liad.sockettest.models.ChatMessage

class ChatAdapter(val secondUser : String) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var messages = mutableListOf<ChatMessage>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.chat_list_item_text_view)
        val itemBackground: LinearLayout = itemView.findViewById(R.id.chat_list_item_background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messages[position]
        currentMessage.from?.let {
            holder.itemBackground.setBackgroundColor(if (secondUser == it.userId) Color.BLUE else Color.GRAY)
            holder.messageText.gravity = if (secondUser == it.userId) Gravity.START else Gravity.END
        }
        holder.messageText.text = currentMessage.message
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyDataSetChanged()
    }

}