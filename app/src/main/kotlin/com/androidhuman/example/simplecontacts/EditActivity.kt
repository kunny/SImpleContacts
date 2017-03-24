package com.androidhuman.example.simplecontacts

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.androidhuman.example.simplecontacts.model.Person
import io.realm.Realm

class EditActivity : AppCompatActivity() {

    lateinit var tlName: TextInputLayout

    lateinit var etName: EditText

    lateinit var etAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        tlName = findViewById(R.id.ti_activity_edit_name) as TextInputLayout
        etName = findViewById(R.id.et_activity_edit_name) as EditText
        etAddress = findViewById(R.id.et_activity_edit_address) as EditText
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
        if (etName.text.length == 0) {
            // TODO: Remove error on content changes
            tlName.error = getText(R.string.msg_name_cannot_be_empty)
            return
        }

        val realm = Realm.getDefaultInstance()

        // Get next id value for primary key
        val currentMaxId = realm.where(Person::class.java).max(Person.PRIMARY_KEY)
        val nextId: Long?
        if (null == currentMaxId) {
            nextId = 0L
        } else {
            nextId = currentMaxId.toLong() + 1
        }

        realm.beginTransaction()

        val person = realm.createObject(Person::class.java, nextId)
        person.name = etName.text.toString()
        person.address = etAddress.text.toString()

        /* Alternative method:
        Person person = new Person();
        person.setId(nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());
        realm.copyToRealm(person);
        */

        realm.commitTransaction()

        realm.close()

        setResult(RESULT_OK)
        finish()
    }

    companion object {

        val REQUEST_CODE = 10
    }
}
