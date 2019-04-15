package br.com.anderltda.funapp.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.content_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        bt_continue.setOnClickListener {

            loading.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())

                .addOnCompleteListener(this) { task ->

                    loading.visibility = View.GONE

                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG" , "createUserWithEmail:success")
                        saveFirestoneDatabase()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@SignUpActivity,
                            task.exception?.message, Toast.LENGTH_LONG).show()
                    }


                }

        }
    }

    private fun saveFirestoneDatabase() {

        val user = User(et_fullname.text.toString(), et_email.text.toString(), et_phone.text.toString())

        FirebaseDatabase.getInstance().getReference("Usuario")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this@SignUpActivity,
                        "Usuário cadastrado com sucesso!",
                        Toast.LENGTH_LONG).show()

                    val intent = Intent()
                    intent.putExtra("email", et_email.text.toString())
                    intent.putExtra("password", et_password.text.toString())
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                } else {
                    Toast.makeText(this@SignUpActivity,
                        it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


}
