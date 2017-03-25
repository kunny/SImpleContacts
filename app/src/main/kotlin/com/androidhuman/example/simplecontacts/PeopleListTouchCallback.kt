package com.androidhuman.example.simplecontacts

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.androidhuman.example.simplecontacts.model.PRIMARY_KEY
import com.androidhuman.example.simplecontacts.model.Person
import io.realm.Realm

class PeopleListTouchCallback(
        private val adapter: PeopleAdapter,
        private val realm: Realm)
    : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val id = adapter.getItemId(viewHolder.adapterPosition)
        val removed = removePerson(id)
        if (removed) {
            adapter.notifyItemRemoved(viewHolder.adapterPosition)
        } else {
            Log.e("PeopleListTouchCallback", "Not removed")
        }
    }

    private fun removePerson(id: Long): Boolean {
        val results = realm.where(Person::class.java).equalTo(PRIMARY_KEY, id).findAll()
        if (results.isNotEmpty()) {

            realm.beginTransaction()
            results.deleteAllFromRealm()
            realm.commitTransaction()

            return true
        } else {
            return false
        }
    }
}