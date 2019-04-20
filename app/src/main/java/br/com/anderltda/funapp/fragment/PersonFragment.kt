package br.com.anderltda.funapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_person.*
import java.text.SimpleDateFormat
import java.util.*


class PersonFragment : Fragment() {

    private lateinit var root: ViewGroup

    private lateinit var auth: FirebaseAuth

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refStates by lazy {
        FirebaseFirestore.getInstance().collection("users")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_person, container, false)

        root = view.findViewById(R.id.root)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = resources.getString(R.string.title_person)
        toolbar.inflateMenu(R.menu.menu_perfil)

        val ref = FirebaseDatabase.getInstance().getReference("USER_DEFAULT")

        ref.child(FirebaseAuth.getInstance()
            .currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (childDataSnapshot in dataSnapshot.children) {

                        if(childDataSnapshot.key.toString() == "name") {
                            et_fullname.setText(childDataSnapshot.value.toString())
                        }

                        if(childDataSnapshot.key.toString() == "email") {
                            //et_email.setText(childDataSnapshot.value.toString())
                        }

                        if(childDataSnapshot.key.toString() == "phone") {
                            et_phone.setText(childDataSnapshot.value.toString())
                        }

                        Log.d("*************: ", childDataSnapshot.key.toString())
                        Log.d("*************: ", childDataSnapshot.value.toString())

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        val button = view.findViewById(R.id.bt_continue) as Button

        button.setOnClickListener {
            saveFirestoneDatabase()
        }

        return view
    }

    private fun saveFirestoneDatabase() {

        val ui = FirebaseAuth.getInstance().currentUser!!.uid

        val sdf = SimpleDateFormat("h:mm a")
        val hora = Calendar.getInstance().getTime()
        val dataFormatada = sdf.format(hora)

        val user = User()
        user.uid = ui
        //user.email = et_email.text.toString()
        user.name = et_fullname.text.toString()
        user.phone = et_phone.text.toString()
        user.image = ""
        user.update = dataFormatada

        FirebaseDatabase.getInstance().getReference("USER_DEFAULT")
            .child(ui)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        activity,
                        "Usuário atualizado com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()


                } else {
                    Toast.makeText(
                        activity,
                        it.exception?.message, Toast.LENGTH_LONG
                    ).show()
                }
            }

        refStates.document(ui).set(user);
    }

    companion object {
        fun newInstance(): PersonFragment {
            val fragment = PersonFragment()
            return fragment
        }
    }


}
