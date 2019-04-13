package br.com.anderltda.funapp.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.anderltda.funapp.R

class TalkFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_talk, container, false)


        return view
    }


    companion object {
        fun newInstance(): TalkFragment {
            val fragment = TalkFragment()
            return fragment
        }
    }

}
