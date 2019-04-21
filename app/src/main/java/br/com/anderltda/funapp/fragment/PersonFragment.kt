package br.com.anderltda.funapp.fragment

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.data.AppDatabase
import br.com.anderltda.funapp.data.dao.AddressDao
import br.com.anderltda.funapp.data.entity.Address
import br.com.anderltda.funapp.model.Contact
import br.com.anderltda.funapp.model.User
import br.com.anderltda.funapp.util.Constants
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

    private val refContactsStates by lazy {
        firestore.collection(Constants.CONTACTS_CHAT_APP_FIREBASE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_person, container, false)

        root = view.findViewById(R.id.root)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = resources.getString(R.string.title_person)
        toolbar.inflateMenu(R.menu.menu_perfil)

        val loading = view.findViewById<LinearLayout>(R.id.loading) as LinearLayout
        loading.visibility = View.VISIBLE

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val user = User()
        user.uid = uid

        val ref = FirebaseDatabase.getInstance().getReference("USER_DEFAULT")

        ref.child(user.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (childDataSnapshot in dataSnapshot.children) {

                        if(childDataSnapshot.key.toString() == "name") {
                            user.name = childDataSnapshot.value.toString()
                            et_fullname.setText(user.name)
                        }

                        if(childDataSnapshot.key.toString() == "email") {
                            user.email = childDataSnapshot.value.toString()
                            et_email.setText(user.email)
                        }

                        if(childDataSnapshot.key.toString() == "phone") {
                            user.phone = childDataSnapshot.value.toString()
                            et_phone.setText(user.phone)
                        }

                        if(childDataSnapshot.key.toString() == "create") {
                            user.create = childDataSnapshot.value.toString()
                        }

                        //Log.d("*************: ", childDataSnapshot.key.toString())
                        //Log.d("*************: ", childDataSnapshot.value.toString())

                    }

                    loading.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.nav_address -> {

                    newFragment(AddressFragment.newInstance(user.name))
                    return@setOnMenuItemClickListener true
                }

            }

            false
        }

        val button = view.findViewById(R.id.bt_continue) as Button

        button.setOnClickListener {
            saveFirestoneDatabase(user)
        }

        return view
    }

    private fun saveFirestoneDatabase(user: User) {

        val sdf = SimpleDateFormat("h:mm a")
        val hora = Calendar.getInstance().getTime()
        val dataFormatada = sdf.format(hora)

        user.name = et_fullname.text.toString()
        user.phone = et_phone.text.toString()
        user.update = dataFormatada

        FirebaseDatabase.getInstance().getReference(Constants.USER_DEFAULT_APP_FIREBASE)
            .child(user.uid)
            .setValue(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.success_message_default),
                        Toast.LENGTH_LONG
                    ).show()


                } else {
                    Toast.makeText(
                        activity,
                        it.exception?.message, Toast.LENGTH_LONG
                    ).show()
                }
            }


        val contact = Contact()
        contact.uid = user.uid
        contact.name = user.name
        contact.email = user.email
        contact.phone = user.phone
        contact.create = user.create
        contact.update = user.update
        refContactsStates.document(user.uid).set(contact);

    }


    companion object {
        fun newInstance(): PersonFragment {
            val fragment = PersonFragment()
            return fragment
        }
    }


    private fun newFragment(fragment: Fragment) {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

}
