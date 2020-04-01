package com.liad.sockettest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var messages = mutableListOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText : TextView = itemView.findViewById(R.id.chat_list_item_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messages[position]
        holder.messageText.text = currentMessage
    }

    fun addMessage(message: String) {
        messages.add(message)
        notifyDataSetChanged()
    }

}