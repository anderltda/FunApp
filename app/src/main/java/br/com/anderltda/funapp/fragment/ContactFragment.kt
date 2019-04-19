package br.com.anderltda.funapp.fragment


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R

import br.com.anderltda.funapp.activity.ChatActivity
import br.com.anderltda.funapp.adapter.ContactAdapter
import br.com.anderltda.funapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ContactFragment : Fragment() {

    private lateinit var root: ViewGroup

    private lateinit var adapter: ContactAdapter

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refStates by lazy {
        firestore.collection("users")
    }

    private var sort = SORT_NAME

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        root = view.findViewById(R.id.root)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        val title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = "Contatos"

        toolbar.inflateMenu(R.menu.menu_add)
        toolbar.inflateMenu(R.menu.menu_edit)
        toolbar.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.new_chat -> {

                    adapter.clear()

                    adapter.startListening()

                    return@setOnMenuItemClickListener true
                }
                R.id.add_contact -> {

                    sort = SORT_NAME

                    snackbar("Sorting by $sort")

                    adapter.clear()

                    adapter.startListening()

                    return@setOnMenuItemClickListener true
                }
            }

            false
        }

        adapter = ContactAdapter({
            refStates.orderBy(sort, Query.Direction.ASCENDING)
        })

        adapter.onDeleteListener = { position ->
            //assume success, otherwise it will be updated in the next query

            val user = adapter.get(position)

            val snapshot = adapter.getSnapshot(position)

            delete(user, snapshot.reference)

        }
        adapter.onUpListener = { position ->

            val user = adapter.get(position)

            val snapshot = adapter.getSnapshot(position)

            incrementPopulation(user, snapshot.reference)

        }
        adapter.onClickListener = { position ->

            val user = adapter.get(position)
            val ui = FirebaseAuth.getInstance().currentUser!!.uid

            val next = Intent(activity, ChatActivity::class.java)
            //next.putExtra("ROOM", user.uid.toString() + ui)
            next.putExtra("ROOM", "ROOM")
            startActivity(next)
        }

        val list = view.findViewById<RecyclerView>(R.id.recyclerView)

        val layoutManager = LinearLayoutManager(context)

        list.adapter = adapter

        list.layoutManager = layoutManager

        adapter.setupOnScrollListener(list, layoutManager)

        adapter.onLoadingMore = {
            log("onLoadingMore")
        }

        adapter.onLoadingMoreComplete = {
            log("onLoadingMoreComplete")
        }

        adapter.onHasLoadedAll = {
            log("onHasLoadedAll")
        }


        return view
    }

    fun log(string: String) {
        Log.d("TEST", string)
    }

    fun snackbar(string: String) {
        Snackbar.make(root, string, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun incrementPopulation(user: User, docRef: DocumentReference) {

        firestore.runTransaction { transaction ->

            val snapshot = transaction.get(docRef)

            val newPopulation = snapshot.getDouble("population")!! + 1

            transaction.update(docRef, "population", newPopulation)

            // Success
            null

        }.addOnSuccessListener {

            log("Transaction success!")

        }.addOnFailureListener { e ->

            e.printStackTrace()

            snackbar("Failed to increment ${user.name}")
        }
    }

    fun delete(user: User, docRef: DocumentReference) {

        docRef.delete()
            .addOnSuccessListener {
                log("Transaction success!")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                snackbar("Failed to delete ${user.name}")
            }
    }

    override fun onStart() {
        super.onStart()
        adapter.clear()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    companion object {

        const val SORT_NAME = "name"

        fun newInstance(): ContactFragment {
            val fragment = ContactFragment()
            return fragment
        }
    }

}