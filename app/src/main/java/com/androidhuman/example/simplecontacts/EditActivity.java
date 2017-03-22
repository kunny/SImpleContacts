package com.androidhuman.example.simplecontacts;

import com.androidhuman.example.simplecontacts.model.Person;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 10;

    TextInputLayout tlName;

    EditText etName;

    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tlName = (TextInputLayout) findViewById(R.id.ti_activity_edit_name);
        etName = (EditText) findViewById(R.id.et_activity_edit_name);
        etAddress = (EditText) findViewById(R.id.et_activity_edit_address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_activity_edit_done == item.getItemId()) {
            applyChanges();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyChanges() {
        if (etName.getText().length() == 0) {
            // TODO: Remove error on content changes
            tlName.setError(getText(R.string.msg_name_cannot_be_empty));
            return;
        }

        Realm realm = Realm.getDefaultInstance();

        // Get next id value for primary key
        Number currentMaxId = realm.where(Person.class).max(Person.PRIMARY_KEY);
        Long nextId;
        if (null == currentMaxId) {
            nextId = 0L;
        } else {
            nextId = currentMaxId.longValue() + 1;
        }

        realm.beginTransaction();

        Person person = realm.createObject(Person.class, nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());

        /* Alternative method:
        Person person = new Person();
        person.setId(nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());
        realm.copyToRealm(person);
        */

        realm.commitTransaction();

        realm.close();

        setResult(RESULT_OK);
        finish();
    }
}
