package br.com.anderltda.funapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.Contact
import br.com.anderltda.funapp.model.User
import br.com.anderltda.funapp.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.loading
import kotlinx.android.synthetic.main.content_sign_up.*
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refStates by lazy {
        firestore.collection(Constants.CONTACTS_CHAT_APP_FIREBASE)
    }

    override fun onCreate(bundle: Bundle?) {

        super.onCreate(bundle)

        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = resources.getString(R.string.title_activity_sign_in)

        val back = toolbar.findViewById(R.id.tv_back) as TextView
        back.visibility = View.VISIBLE
        back.setOnClickListener {
            finish()
        }

        bt_continue.setOnClickListener {

            loading.visibility = View.VISIBLE

            if (et_fullname.text.toString().isNotBlank()
                && et_phone.text.toString().isNotBlank()
                && et_email.text.toString().isNotBlank()
                && et_password.text.toString().isNotBlank()
            ) {

                auth.createUserWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())

                    .addOnCompleteListener(this) { task ->

                        loading.visibility = View.GONE

                        if (task.isSuccessful) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success")
                            saveFirestoneDatabase()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@SignUpActivity,
                                task.exception?.message, Toast.LENGTH_LONG
                            ).show()
                        }

                    }

            } else {

                loading.visibility = View.GONE
                Toast.makeText(this@SignUpActivity, resources.getString(R.string.erro_message_fields_required),
                    Toast.LENGTH_LONG).show()

            }

        }
    }

    private fun saveFirestoneDatabase() {

        val ui = FirebaseAuth.getInstance().currentUser!!.uid

        val sdf = SimpleDateFormat("h:mm a")
        val hora = Calendar.getInstance().getTime()
        val dataFormatada = sdf.format(hora)

        val user = User()
        user.uid = ui
        user.email = et_email.text.toString()
        user.name = et_fullname.text.toString()
        user.phone = et_phone.text.toString()
        user.create = dataFormatada

        FirebaseDatabase.getInstance().getReference(Constants.USER_DEFAULT_APP_FIREBASE)
            .child(ui)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this@SignUpActivity,
                        resources.getString(R.string.success_message_default),
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent()
                    intent.putExtra("email", et_email.text.toString())
                    intent.putExtra("password", et_password.text.toString())
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        it.exception?.message, Toast.LENGTH_LONG
                    ).show()
                }
            }

        val contact = Contact()
        contact.uid = user.uid
        contact.name = user.name
        contact.phone = user.phone
        contact.create = user.create
        refStates.document(ui).set(contact);
    }
}