package br.com.anderltda.funapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.adapter.ItemAdapter
import br.com.anderltda.funapp.model.State
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main2.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.item.*


class Main2Activity : AppCompatActivity() {

    companion object {
        const val SORT_NAME = "name"
        const val SORT_POPULATION = "population"
    }

    private lateinit var root: ViewGroup
    private lateinit var adapter: ItemAdapter

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refStates by lazy {
        firestore.collection("talks")
    }

    private var sort = SORT_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        root = findViewById(R.id.root)
       /* val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.refresh)
        toolbar.inflateMenu(R.menu.sort)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_refresh -> {
                    adapter.clear()
                    adapter.startListening()
                    return@setOnMenuItemClickListener true
                }
                R.id.action_sort -> {
                    if (sort == SORT_NAME) sort = SORT_POPULATION else sort = SORT_NAME
                    snackbar("Sorting by $sort")
                    adapter.clear()
                    adapter.startListening()
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
*/
        adapter = ItemAdapter({
            refStates.limit(10)
                .orderBy(sort, Query.Direction.ASCENDING)
        })

        adapter.onDeleteListener = { position ->
            //assume success, otherwise it will be updated in the next query
            val state = adapter.get(position)
            val snapshot = adapter.getSnapshot(position)
            delete(state, snapshot.reference)
        }

        adapter.onUpListener = { position ->
            val state = adapter.get(position)
            val snapshot = adapter.getSnapshot(position)
            incrementPopulation(state, snapshot.reference)
            //shows us waiting for the update
        }
        adapter.onClickListener = { position ->
            Snackbar.make(root, "$position clicked", Snackbar.LENGTH_SHORT)
                .show()
        }

        val list = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
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

        bt_send.setOnClickListener {

            send(et_message.text.toString())
            Toast.makeText(this, et_message.text, Toast.LENGTH_LONG).show()

            et_message.clearFocus()
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

    fun log(string: String) {
        Log.d("TEST", string)
    }

    fun snackbar(string: String) {
        Snackbar.make(root, string, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun incrementPopulation(state: State, docRef: DocumentReference) {
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
            snackbar("Failed to increment ${state.name}")
        }
    }

    fun delete(state: State, docRef: DocumentReference) {
        docRef.delete()
            .addOnSuccessListener {
                log("Transaction success!")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                snackbar("Failed to delete ${state.name}")
            }
    }


    fun send(message: String) {

        // Add a new document with a generated id.
        val data = HashMap<String, String>()
        data.put("name", "Anderson")
        data.put("message", message)

        refStates.add(data)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                log("Transaction success!")
            })
            .addOnFailureListener(OnFailureListener { e ->
                log("Transaction success!")
            })


    }
}
