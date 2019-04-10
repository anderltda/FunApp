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

/**
 * A simple [Fragment] subclass.
 *
 */
class SettingFragment : Fragment() {


    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        val toggleButton = view.findViewById(R.id.toggleButton) as ToggleButton

        toggleButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

                FirebaseAuth.getInstance().signOut()

                Toast.makeText(activity,"Turned On",Toast.LENGTH_LONG).show()

            } else {

                Toast.makeText(activity,"Turned Off",Toast.LENGTH_LONG).show()
            }
        })


        // Inflate the layout for this fragment
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //Toast.makeText(context,"Turned On",Toast.LENGTH_LONG).show()
    }


    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            return fragment
        }
    }

    private fun removerUser() {
        auth = FirebaseAuth.getInstance();

        if (auth.currentUser != null) {
            auth.currentUser!!.delete()
            Log.d("Removido", auth.toString())
        }
    }



}
