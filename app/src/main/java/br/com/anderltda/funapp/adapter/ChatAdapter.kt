package br.com.anderltda.funapp.adapter

import android.view.ViewGroup
import br.com.anderltda.funapp.viewholder.ChatViewHolder
import br.com.anderltda.funapp.model.Chat
import com.commit451.firestoreadapter.FirestoreAdapter
import com.commit451.firestoreadapter.QueryCreator
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(query: QueryCreator) : FirestoreAdapter<Chat, ChatViewHolder>(Chat::class.java, query) {

    var onDeleteListener: ((position: Int) -> Unit)? = null

    var onUpListener: ((position: Int) -> Unit)? = null

    var onClickListener: ((position: Int) -> Unit)? = null

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance();
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ChatViewHolder {

        val chatData = get(position)

        var index = 0

        if (chatData.uid != auth.currentUser!!.uid) {
            index = 1
        }

        val holder = ChatViewHolder.inflate(parent, index)

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

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(get(position))

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}