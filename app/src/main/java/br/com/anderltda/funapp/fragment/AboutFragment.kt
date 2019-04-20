package br.com.anderltda.funapp.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class AboutFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_about)
        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.nav_logout -> {

                    logout()

                    return@setOnMenuItemClickListener true
                }

            }

            false
        }

        return view
    }

    private fun logout() {

        FirebaseAuth.getInstance().signOut()

        val next = Intent(activity, LoginActivity::class.java)
        startActivity(next)
        activity!!.finish()
    }

    private fun excluirUserFirebaseAuth() {

        FirebaseAuth.getInstance().signOut()

        auth = FirebaseAuth.getInstance();

        if (auth.currentUser != null) {
            auth.currentUser!!.delete()
            Log.d("Removido", auth.toString())
        }

        val next = Intent(activity, LoginActivity::class.java)
        startActivity(next)
        activity!!.finish()
    }

    companion object {
        fun newInstance(): AboutFragment {
            val fragment = AboutFragment()
            return fragment
        }
    }
}
