package br.com.anderltda.funapp.fragment


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import br.com.anderltda.funapp.R

import br.com.anderltda.funapp.activity.MainActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {


    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)


        return view
    }

    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            return fragment
        }
    }



}
