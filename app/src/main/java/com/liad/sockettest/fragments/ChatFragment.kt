package com.liad.sockettest.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.liad.sockettest.ChatAdapter
import com.liad.sockettest.R
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    companion object {
        fun newInstance(args: Bundle? = null): ChatFragment {
            val chatFragment = ChatFragment()
            chatFragment.arguments = args
            return chatFragment
        }
    }

    private lateinit var chatAdapter : ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_chat, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        initRV()
    }

    private fun initRV() {
        chatAdapter = ChatAdapter()
        chat_fragment_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatAdapter
        }
    }

}
