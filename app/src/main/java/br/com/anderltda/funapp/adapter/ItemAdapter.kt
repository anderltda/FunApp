package br.com.anderltda.funapp.adapter

import android.view.ViewGroup
import br.com.anderltda.funapp.activity.ItemViewHolder
import com.commit451.firestoreadapter.FirestoreAdapter
import com.commit451.firestoreadapter.QueryCreator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ItemAdapter(query: QueryCreator) : FirestoreAdapter<ChatData, ItemViewHolder>(ChatData::class.java, query) {

    var onDeleteListener: ((position: Int) -> Unit)? = null

    var onUpListener: ((position: Int) -> Unit)? = null

    var onClickListener: ((position: Int) -> Unit)? = null

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {

        val chatData = get(position)

        var index = 0

        if (chatData.uid != auth.currentUser!!.uid) {
            index = 1
        }

        val holder = ItemViewHolder.inflate(parent, index)

/*        holder.buttonDelete.setOnClickListener {

            val position = holder.adapterPosition

            onDeleteListener?.invoke(position)

        }
        holder.buttonUp.setOnClickListener {

            val position = holder.adapterPosition

            onUpListener?.invoke(position)

        }
        holder.itemView.setOnClickListener {

            val position = holder.adapterPosition

            onClickListener?.invoke(position)

        }*/

        return holder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(get(position))

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}