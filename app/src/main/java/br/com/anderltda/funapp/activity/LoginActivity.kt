package br.com.anderltda.funapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.anderltda.funapp.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    private val CADASTRO_REQUEST_CODE = 1

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance();

        if (auth.currentUser != null) {
            nextHome()
        }

        buttonLogin.setOnClickListener {

            loading.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {

                loading.visibility = View.GONE

                if (it.isSuccessful) {
                    nextHome()
                } else {
                    Toast.makeText(this@LoginActivity, it.exception?.message,
                        Toast.LENGTH_LONG).show()
                }
            }
        }

        buttonSignin.setOnClickListener {
            val next = Intent(this, SignUpActivity::class.java)
            startActivityForResult(next, CADASTRO_REQUEST_CODE)
        }


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun nextHome() {

        val next = Intent(this, MainActivity::class.java)
        startActivity(next)
        finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CADASTRO_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        email.setText(data?.getStringExtra("email"))
                        password.setText(data?.getStringExtra("password"))
                    }
                }
            }
        }
    }


}
