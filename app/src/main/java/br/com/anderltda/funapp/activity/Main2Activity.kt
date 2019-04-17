package br.com.anderltda.funapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import br.com.anderltda.funapp.R
import br.com.anderltda.funapp.adapter.ChatData
import br.com.anderltda.funapp.adapter.ItemAdapter
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main2.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent




class Main2Activity : AppCompatActivity() {

    companion object {
        const val SORT_NAME = "name"
        const val SORT_POPULATION = "population"
    }

    private lateinit var root: ViewGroup
    private lateinit var adapter: ItemAdapter
    private lateinit var auth: FirebaseAuth

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val refStates by lazy {

        val it = intent

        val room = it.getStringExtra("ROOM")

        firestore.collection(room)
    }

    private var sort = SORT_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        auth = FirebaseAuth.getInstance();

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
            refStates.limit(200)
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
            //Toast.makeText(this, et_message.text, Toast.LENGTH_LONG).show()

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

    fun incrementPopulation(chat: ChatData, docRef: DocumentReference) {
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
            snackbar("Failed to increment ${chat.name}")
        }
    }

    fun delete(chat: ChatData, docRef: DocumentReference) {
        docRef.delete()
            .addOnSuccessListener {
                log("Transaction success!")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                snackbar("Failed to delete ${chat.name}")
            }
    }


    fun send(message: String) {

        val ref = FirebaseDatabase.getInstance().getReference("USER_DEFAULT")

        ref.child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childDataSnapshot in dataSnapshot.children) {
                    Log.d("PARENT: ", childDataSnapshot.value.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        val sdf = SimpleDateFormat("h:mm a")
        val hora = Calendar.getInstance().getTime() // Ou qualquer outra forma que tem
        val dataFormatada = sdf.format(hora)

        val chat = ChatData()
        chat.name = "Anderson"
        chat.uid =  auth.currentUser!!.uid.toString()
        chat.time = dataFormatada
        chat.message = message
        refStates.document().set(chat);

    }

}
