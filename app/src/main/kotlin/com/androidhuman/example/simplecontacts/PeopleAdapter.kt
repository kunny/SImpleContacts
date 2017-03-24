package com.androidhuman.example.simplecontacts

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.androidhuman.example.simplecontacts.model.Person

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.PersonHolder>() {

    private var people: List<Person>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        return PersonHolder(parent)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        val person = people!![position]

        holder.name.text = person.name
        holder.address.text = if (!TextUtils.isEmpty(person.address))
            person.address
        else
            "(No address)"
    }

    override fun getItemId(position: Int): Long {
        return people!![position].id
    }

    override fun getItemCount(): Int {
        return if (null != people) people!!.size else 0
    }

    fun setPeople(people: List<Person>?) {
        this.people = people
    }

    inner class PersonHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_person, parent, false)) {

        var name: TextView

        var address: TextView

        init {

            name = itemView.findViewById(R.id.tv_item_person_name) as TextView
            address = itemView.findViewById(R.id.tv_item_person_address) as TextView

            itemView.setOnClickListener {
                // TODO: Start an activity to edit an item.
            }
        }
    }

}
