package br.com.anderltda.funapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.fragment.TalkFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*


class LoginActivity : AppCompatActivity() {

    private val CADASTRO_REQUEST_CODE = 1

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.add_contact -> {

                    val next = Intent(this, SignUpActivity::class.java)

                    startActivityForResult(next, CADASTRO_REQUEST_CODE)

                    return@setOnMenuItemClickListener true
                }

            }

            false
        }

        auth = FirebaseAuth.getInstance();

        if (auth.currentUser != null) {
            nextHome()
        }

        bt_login.setOnClickListener {

            loading.visibility = View.VISIBLE

            if(et_login.text.toString().isNotBlank() && et_password.text.toString().isNotBlank())  {

                auth.signInWithEmailAndPassword(et_login.text.toString(), et_password.text.toString())
                    .addOnCompleteListener {

                        loading.visibility = View.GONE

                        if (it.isSuccessful) {
                            nextHome()
                        } else {
                            Toast.makeText(this@LoginActivity, it.exception?.message,
                                Toast.LENGTH_LONG).show()
                        }
                    }

            } else {
                loading.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Informe login senha",
                    Toast.LENGTH_LONG).show()
            }

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
                        et_login.setText(data?.getStringExtra("email"))
                        et_password.setText(data?.getStringExtra("password"))
                    }
                }
            }
        }
    }


}
