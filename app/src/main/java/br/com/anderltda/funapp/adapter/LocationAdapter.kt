package br.com.anderltda.funapp.adapter

import android.view.ViewGroup
import br.com.anderltda.funapp.activity.ContactViewHolder
import br.com.anderltda.funapp.activity.LocationViewHolder
import br.com.anderltda.funapp.model.User
import com.commit451.firestoreadapter.FirestoreAdapter
import com.commit451.firestoreadapter.QueryCreator


class LocationAdapter(query: QueryCreator) : FirestoreAdapter<User, LocationViewHolder>(User::class.java, query) {

    var onDeleteListener: ((position: Int) -> Unit)? = null

    var onUpListener: ((position: Int) -> Unit)? = null

    var onClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {

        val holder = LocationViewHolder.inflate(parent)

        /*holder.buttonDelete.setOnClickListener {

            val position = holder.adapterPosition

            onDeleteListener?.invoke(position)

        }
        holder.buttonUp.setOnClickListener {

            val position = holder.adapterPosition

            onUpListener?.invoke(position)

        }*/
        holder.itemView.setOnClickListener {

            val position = holder.adapterPosition

            onClickListener?.invoke(position)

        }

        return holder
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(get(position))
    }
}