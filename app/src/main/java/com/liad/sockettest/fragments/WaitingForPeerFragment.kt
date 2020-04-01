package com.liad.sockettest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liad.sockettest.R

class WaitingForPeerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_waiting_for_peer, container, false)


    companion object{

        fun newInstance(args : Bundle? = null) : WaitingForPeerFragment {
            val waitingForPeerFragment =
                WaitingForPeerFragment()
            waitingForPeerFragment.arguments = args
            return waitingForPeerFragment
        }
    }
}
