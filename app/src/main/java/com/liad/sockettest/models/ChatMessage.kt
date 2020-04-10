package com.liad.sockettest.models

data class ChatMessage(
    val to: String,
    val message: String,
    val from: ChatUser?,
    val timestamp: Long,
    val type: String
)
