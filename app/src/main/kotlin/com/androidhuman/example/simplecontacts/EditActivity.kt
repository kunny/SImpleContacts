package com.androidhuman.example.simplecontacts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.androidhuman.example.simplecontacts.model.PRIMARY_KEY
import com.androidhuman.example.simplecontacts.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

const val REQUEST_CODE = 10

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        intent.extras?.run {
            fetchData(getLong(PRIMARY_KEY))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_edit_done == item.itemId) {
            applyChanges()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyChanges() {
        if (et_activity_edit_name.text.isEmpty()) {
            // TODO: Remove error on content changes
            ti_activity_edit_name.error = getText(R.string.msg_name_cannot_be_empty)
            return
        }

        Realm.getDefaultInstance().run {

            val personId = if (-1L != intent?.getLongExtra(PRIMARY_KEY, -1)) {
                intent.extras.getLong(PRIMARY_KEY)
            } else {
                // Get next id value for primary key
                val currentMaxId = where(Person::class.java).max(PRIMARY_KEY)
                if (null == currentMaxId) 0L else currentMaxId.toLong() + 1
            }

            beginTransaction()

            val person = Person().apply {
                id = personId
                name = et_activity_edit_name.text.toString()
                address = et_activity_edit_address.text.toString()
            }

            // insert or update
            copyToRealmOrUpdate(person)

            commitTransaction()
            close()
        }

        setResult(RESULT_OK)
        finish()
    }

    private fun fetchData(id: Long) {
        Realm.getDefaultInstance().run {
            val person = checkNotNull(
                    where(Person::class.java).equalTo(PRIMARY_KEY, id).findFirst())

            et_activity_edit_name.setText(person.name)
            et_activity_edit_address.setText(person.address)

            close()
        }
    }
}
