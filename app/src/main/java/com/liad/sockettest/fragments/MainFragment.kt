package com.liad.sockettest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.nkzawa.socketio.client.Socket
import com.liad.sockettest.R
import com.liad.sockettest.managers.SocketWebManager
import com.liad.sockettest.utills.extensions.changeFragment
import com.liad.sockettest.utills.extensions.log
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance(args: Bundle? = null): MainFragment {
            val mainFragment =
                MainFragment()
            mainFragment.arguments = args
            return mainFragment
        }
    }

    private val mSocket = SocketWebManager.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSocket()
        startOnClickListener()
    }

    private fun initSocket() {
        // TODO Liad - delete in production (only for testing)
        mSocket.disconnect()
        mSocket.on(Socket.EVENT_CONNECT) {
            log("Connected!")
        }

        mSocket.connect()
    }

    private fun startOnClickListener() {
        activity?.also { activity ->
            main_fragment_start_text_view.setOnClickListener {
                changeFragment(activity, WaitingForPeerFragment.newInstance(), addToStack = true)
            }
        }
    }

}
